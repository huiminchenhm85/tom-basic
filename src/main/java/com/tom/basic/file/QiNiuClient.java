package com.tom.basic.file;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.google.common.base.Strings;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.processing.OperationManager;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.BatchStatus;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;


public class QiNiuClient {
	
	private static Logger LOGGER = Logger.getLogger("qiniu");
	
	public static final String ZOOM_W_80_INTERLACE_HIGH_QUALITY = "imageMogr2/thumbnail/80x/quality/80/interlace/1";
	public static final String ZOOM_W_320_INTERLACE_HIGH_QUALITY = "imageMogr2/thumbnail/320x/quality/80/interlace/1";
	public static final String ZOOM_W_400_INTERLACE_HIGH_QUALITY = "imageMogr2/thumbnail/400x/quality/90/interlace/1";
	public static final String ZOOM_W_400_INTERLACE = "imageView2/0/w/400/interlace/1";
	public static final String ZOOM_W_200_INTERLACE = "imageView2/0/w/200/interlace/1";
	public static final String ZOOM_W_152_INTERLACE = "imageView2/0/w/152/interlace/1";
	public static final String ZOOM_W_320_INTERLACE = "imageView2/0/w/320/interlace/1";

	private UploadManager uploadManager;
	private Auth auth;
	public  String BUCKET_KIKUU ;
	private String NOTIFY_URL ;

	
	public static QiNiuClient getInstance(String QINIU_AK,String QINIU_SK,String bucket,String notifyUrl){
		QiNiuClient newClient = new QiNiuClient();
		newClient.uploadManager = new UploadManager();
		newClient.auth = Auth.create(QINIU_AK, QINIU_SK);
		newClient.BUCKET_KIKUU = bucket;
		newClient.NOTIFY_URL = notifyUrl;
		return newClient;
	}

	public  boolean upload(byte[] data, String key){
		  try {
		        Response res = uploadManager.put(data, key, auth.uploadToken("kikuu"));
		        if(res.isOK()){
		        	return true;
		        }else {
		        	LOGGER.warn(res);
		        	return false;
		        }
		    } catch (QiniuException e) {
		        Response r = e.response;
		        try {
		        	if(r==null){
		        		LOGGER.error("uploade file 2 qiniu failed.body", e);
		        	} else {
		        		LOGGER.error("uploade file 2 qiniu failed.body msg ="+r.bodyString(),e);
		        	}
		        } catch (QiniuException e1) {
		            //ignore
		        }
		        return false;
		    } 
	}
	
	public  String getUploadTokenForJS(){
		return auth.uploadToken(BUCKET_KIKUU);
	}

		public  boolean uploadFile(File file,String key){
		    try {
		        Response res = uploadManager.put(file, key, auth.uploadToken("kikuu"));
		        if(res.isOK()){
		        	return true;
		        }else {
		        	LOGGER.warn(res);
		        	return false;
		        }
		    } catch (QiniuException e) {
		    	 Response r = e.response;
		    	 if(r == null){
		    		 LOGGER.error("uploade file 2 qiniu failed",e);
		    	 } else {
			        LOGGER.error("uploade file 2 qiniu failed.msg ="
							+ r.toString(),e);
		    	 }
		        try {
		        	  LOGGER.error("uploade file 2 qiniu failed.body msg ="+r.bodyString());
		        } catch (QiniuException e1) {
		            //ignore
		        }
		        return false;
		    }
		}
		
		public  boolean uploadFile(File file,String key,String... operates){
			try {
				String fops = StringUtils.join(operates, ';');
				String zoomAuthArgument = getPersistentUpToken(fops);
				Response res = uploadManager.put(file, key, zoomAuthArgument);
				if(res.isOK()){
					return true;
				} else {
					LOGGER.warn(res);
					return false;
				}
			} catch (QiniuException e) {
				Response r = e.response;
				LOGGER.error("upload file 2 qiniu and zoom to small size failed.body msg="
						+ r.toString(),e);
				try {
					LOGGER.error("upload file 2 qiniu and zoom to small size failed.body msg="+r.bodyString());
				} catch (QiniuException e1) {
					//ignore
				}
				return false;
			}
		}
		
		public  boolean uploadFile(File file,String key,String key1,String key2){
		    try {
		        Response res = uploadManager.put(file, key, getImageUpToken(key1,key2));
		        if(res.isOK()){
		        	return true;
		        }else {
		        	LOGGER.warn(res);
		        	return false;
		        }
		    } catch (QiniuException e) {
		    	 Response r = e.response;
			        LOGGER.error("uploade file 2 qiniu failed.msg ="
							+ r.toString(),e);
			        try {
			        	  LOGGER.error("uploade file 2 qiniu failed.body msg ="+r.bodyString());
			        } catch (QiniuException e1) {
			            //ignore
			        }
			        return false;
		    }
		}
		
		
//		public static String pipleString3 = "imageView2/0/w/200/interlace/1|saveas/a2lrdXUtcHJvZHVjdDpoYWhhYXNzcy5qcGc=;imageView2/0/w/500/interlace/1|saveas/a2lrdXUtcHJvZHVjdDpoYWhhLmpwZw==";
		
		private  String getImageUpToken(String key1,String key2){
			return auth.uploadToken(BUCKET_KIKUU, null, 3600, new StringMap()
					.putNotEmpty("persistentOps", getPersistentOps(key1,key2))
					.putNotEmpty("persistentNotifyUrl", NOTIFY_URL)
					.putNotEmpty("persistentPipeline", ""),true);
		}
		
		private  String getPersistentUpToken(String operates){
			if(operates == null){
				return null;
			}
			return auth.uploadToken(BUCKET_KIKUU, null, 3600, new StringMap()
					.putNotEmpty("persistentOps", operates)
					.putNotEmpty("persistentNotifyUrl", NOTIFY_URL)
					.putNotEmpty("persistentPipeline", ""),true);
		}
		
		private  String getPersistentOps(String key1,String key2){
			String ops1 = ZOOM_W_400_INTERLACE + "|saveas/"+UrlSafeBase64.encodeToString(BUCKET_KIKUU+":"+key1);
			String ops2 = ZOOM_W_200_INTERLACE + "|saveas/"+UrlSafeBase64.encodeToString(BUCKET_KIKUU+":"+key2);
			String persistentOps = ops1+";"+ops2;
			return persistentOps;
		}
		
		public  boolean dataProcessing(String bucket,String originImageKey,String... operates){
			OperationManager operater = new OperationManager(auth);
			String notifyUrl = NOTIFY_URL;
			boolean force = true;
			String pipeline = "";
			StringMap params = new StringMap().putNotEmpty("notifyURL", notifyUrl)
					.putWhen("force", 1, force).putNotEmpty("pipeline",pipeline);
			String fops = StringUtils.join(operates, ';');
			try{
				String id = operater.pfop(bucket, originImageKey, fops, params);
				if(Strings.isNullOrEmpty(id)){
					LOGGER.warn(originImageKey+" trans image failed");
					return false;
				} else{
					return true;
				}
				//String url = "http://api.qiniu.com/status/get/profop?id="+id;
			} catch(QiniuException e){
				Response r = e.response;
		        LOGGER.error("operate image from qiniu failed.msg ="
						+ r.toString(),e);
		        try {
		        	  LOGGER.error("operate image from qiniu failed.body msg ="+r.bodyString());
		        } catch (QiniuException e1) {
		            //ignore
		        }
		        return false;
			}
		}
		
		public  String getFops(String operate,String bucket,String desImageKey){
			return operate+"|saveas/"+UrlSafeBase64.encodeToString(bucket+":"+desImageKey);
		}
		
		public  boolean renameImage(String bucket,String originKey,String desKey){
			BucketManager bucketManager = new BucketManager(auth);
			try {
				bucketManager.rename(bucket, originKey, desKey);
				return true;
			} catch (QiniuException e) {
				LOGGER.error("operate image from qiniu failed",e);
				return false;
			}
			
		}
		
		public  boolean renameTwoImage(String bucket,String originKey1,String desKey1,String originKey2,String desKey2){
			BucketManager bucketManager = new BucketManager(auth);
			BucketManager.Batch ops = new BucketManager.Batch()
					.rename(bucket, originKey1, desKey1)
					.rename(bucket, originKey2, desKey2);
			LOGGER.info("starting rename two image in "+bucket+">>>>>>>>>>>>>>>>>>");
			LOGGER.info(originKey1+"-->"+desKey1);
			LOGGER.info(originKey2+"-->"+desKey2);
			try{
				Response r = bucketManager.batch(ops);
				BatchStatus[] bs = r.jsonToObject(BatchStatus[].class);
				int count = bs.length;
				int i = 0;
				for (BatchStatus b : bs) {
			        if(200==b.code){
			        	LOGGER.info(i+" in "+count+" rename image success");
			        } else{
			        	LOGGER.info(i+" in "+count+" rename image failed");
			        }
			        i++;
			    }
				return true;
			}catch(QiniuException e){
				Response r = e.response;
		        LOGGER.error("operate image from qiniu failed.msg ="
						+ r.toString(),e);
		        try {
		        	  LOGGER.error("operate image from qiniu failed.body msg ="+r.bodyString());
		        } catch (QiniuException e1) {
		            //ignore
		        }
		        return false;
			} finally{
				LOGGER.info("end rename two image in "+bucket+">>>>>>>>>>>>>>>>>>");
			}
		}
		
//		public static void main(String[] args){
//			File file = new File("D:/WorkDocument/Kikuu/imageFile/imageNeedToPs/2015-09-18/k_04.jpg");
//			System.out.println(uploadFile1(file,"test_2015-10-08","test_2015-10-08_250_250","test_2015-10-08_360_480"));
//		} 	
}
