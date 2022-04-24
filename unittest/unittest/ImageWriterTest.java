package unittest;

import org.junit.jupiter.api.Test;
import primitives.Color;
import renderer.ImageWriter;

import static org.junit.jupiter.api.Assertions.*;



public class ImageWriterTest {
    public ImageWriterTest() {
    }

    @Test
    public void ImageWiterWriteToImageTest() {
        ImageWriter imageWriter = new ImageWriter("image_01", 800, 500);
        int Nx = imageWriter.getNx();
        int Ny = imageWriter.getNy();

        for(int i = 0; i < Ny; ++i) {
            for(int j = 0; j < Nx; ++j) {
                Color purple;
                if (i % 50 != 0 && j % 50 != 0) {
                    purple = new Color(java.awt.Color.WHITE);
                    imageWriter.writePixel(j, i, purple);
                } else {
                    purple = new Color(100.0D, 0.0D, 100.0D);
                    imageWriter.writePixel(j, i, purple);
                }
            }
        }

        imageWriter.writeToImage();
    }
}
