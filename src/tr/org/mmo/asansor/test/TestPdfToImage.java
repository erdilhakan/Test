package tr.org.mmo.asansor.test;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;

public class TestPdfToImage {

    /**
     * @param args
     * @throws IOException 
     */
    @SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        File pdfFile = new File("d:\\yenirapor.pdf");
        RandomAccessFile raf = new RandomAccessFile(pdfFile, "r");
        FileChannel channel = raf.getChannel();
        ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
        PDFFile pdf = new PDFFile(buf);
        Image i=createImage(pdf.getPage(0), null);
        System.out.println(i);
        /*
        for (int i=0; i<pdf.getNumPages(); i++)
            createImage(pdf.getPage(i), "c:\\PICTURE_"+i+".jpg");
            */

    }

    @SuppressWarnings("unused")
	public static Image createImage(PDFPage page, String destination) throws IOException{
        Rectangle rect = new Rectangle(0, 0, (int) page.getBBox().getWidth(),
                (int) page.getBBox().getHeight());
        BufferedImage bufferedImage = new BufferedImage(rect.width, rect.height,
                         BufferedImage.TYPE_INT_RGB);

        Image image = page.getImage(rect.width, rect.height,    // width & height
                   rect,                       // clip rect
                   null,                       // null for the ImageObserver
                   true,                       // fill background with white
                   true                        // block until drawing is done
        );
        return image;
        /*
        Graphics2D bufImageGraphics = bufferedImage.createGraphics();
        bufImageGraphics.drawImage(image, 0, 0, null);
        ImageOutputStream out=null;
        
        ImageIO.write(bufferedImage, "JPG", out);
        return out;
        */
    }

}