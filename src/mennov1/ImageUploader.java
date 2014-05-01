package mennov1;

import java.util.EventObject;
import library.SewerSender;
import events.PictureEvent;

import java.io.File;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ImageUploader implements Listener<PictureEvent> {

	private String fname;
	private String url;

	public ImageUploader(String url) {
		this.url = url;
	}

	@Override
	public Boolean wants(EventObject e) {
		return (e instanceof PictureEvent);
	}

	@Override
	public void event(PictureEvent e) {
		try {
			if (null != url) {
				// Maak een jpg bytestream van het plaatje (in geheugen)
				ByteArrayOutputStream os = new ByteArrayOutputStream();
	            ImageIO.write(e.getImage(), "jpg", os);
	            InputStream imageInByte = new ByteArrayInputStream(os.toByteArray());


	            // UPLOAD
	            HttpPost httppost = new HttpPost(url);

	            HttpClient httpclient = new DefaultHttpClient();
	            httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
	            
	            // dit gaat voor php naar $_FILES
	            HttpEntity httpEntity = MultipartEntityBuilder.create()
	                .addBinaryBody("uploadedfile", imageInByte, ContentType.create("image/jpeg"), "cam.jpg")
	                .build();

	            httppost.setEntity(httpEntity);
	            HttpResponse response = httpclient.execute(httppost);
	            HttpEntity resEntity = response.getEntity();
	            // System.out.println(response.getStatusLine()); // server output
	            if (resEntity != null) {
	              // System.out.println(EntityUtils.toString(resEntity));
	            }
	            if (resEntity != null) {
	              resEntity.consumeContent();
	            }

	            httpclient.getConnectionManager().shutdown();
	        }
		} catch (Exception ex) { SewerSender.println(ex.toString()); }
	}

}
