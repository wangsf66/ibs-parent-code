package com.ibs.code.service.file;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import com.douglei.tools.file.FileUtil;
import com.ibs.code.service.BasicService;
import com.ibs.components.filters.log.core.filter.LogContext;
import com.ibs.components.filters.log.entity.LogOperation;
import com.ibs.components.response.ResponseSuccess;

/**
 * 
 * @author DougLei
 */
public abstract class FileService extends BasicService{
	private static final byte[] NON_EXISTS = "不存在该文件, 请联系系统管理员".getBytes(StandardCharsets.UTF_8);
	
	/**
	 * 从当前操作系统中下载文件
	 * @param response
	 * @param file
	 * @throws DownloadFileException 
	 */
	protected void download(HttpServletResponse response, DownloadFile file) throws DownloadFileException {
		response.setHeader("Content-Type", "application/octet-stream;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename=" + (file.exists()?file.getName():file.getNameWhenFileNonExists()));
		
		try {
			OutputStream out = response.getOutputStream();
			if(file.exists()) {
				FileUtil.copyFile(file.getFile(), out);
				if(LogContext.unEmpty()) {
					LogOperation operationLog = LogContext.getOperationLog();
					operationLog.setIsSuccess(ResponseSuccess.SUCCESS.getCode());
					operationLog.getRequestLog().setRespBody(file.getName());
				}
			}else {
				out.write(NON_EXISTS);
				if(LogContext.unEmpty()) {
					LogOperation operationLog = LogContext.getOperationLog();
					operationLog.setIsSuccess(ResponseSuccess.FAILURE.getCode());
					operationLog.getRequestLog().setRespBody("不存在要下载的文件["+file.getName()+"]");;
				}
			}
		} catch (IOException e) {
			throw new DownloadFileException(file, e);
		}
	}
	
	/**
	 * 从当前操作系统中批量下载
	 * @param response
	 * @param files
	 * @throws DownloadFileException 
	 */
	protected void batchDownload(HttpServletResponse response, List<DownloadFile> files) throws DownloadFileException {
		response.setHeader("Content-Type", "application/octet-stream;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="+System.currentTimeMillis()+".zip");
		
		StringBuilder downloadFileNames = new StringBuilder(80);// 下载的文件名
		DownloadFile currentFile = null;
		try (ZipOutputStream zipStream = new ZipOutputStream(response.getOutputStream())){
			InputStream in = null;
			byte[] b = new byte[1024];
			short length;
			ZipEntry zipEntry;
			for (DownloadFile file : files) {
				currentFile = file;
				if(currentFile.exists()) {
					zipStream.putNextEntry(zipEntry = new ZipEntry(file.getName()));
					in = new FileInputStream(currentFile.getFile());
					while((length = (short)in.read(b))>0) {
						zipStream.write(b, 0, length);
					}
				}else {
					zipStream.putNextEntry(zipEntry = new ZipEntry(file.getNameWhenFileNonExists()));
					zipStream.write(NON_EXISTS);
				}
				downloadFileNames.append(",").append(zipEntry.getName());
				
				in.close();
				zipStream.closeEntry();
			}
			if(LogContext.unEmpty()) {
				LogOperation operationLog = LogContext.getOperationLog();
				operationLog.setIsSuccess(ResponseSuccess.SUCCESS.getCode());
				operationLog.getRequestLog().setRespBody(downloadFileNames.substring(1));
			}
		} catch (IOException e) {
			throw new DownloadFileException(currentFile, e);
		}
	}
}