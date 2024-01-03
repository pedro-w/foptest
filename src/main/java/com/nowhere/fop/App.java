package com.nowhere.fop;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws Exception {
        FopFactory fopFactory = FopFactory.newInstance(new File("fop.xconf").getAbsoluteFile());

        // Step 2: Set up output stream.
        // Note: Using BufferedOutputStream for performance reasons (helpful with
        // FileOutputStreams).
        OutputStream out = new BufferedOutputStream(new FileOutputStream(new File("myfile.pdf")));

        try {
            // Step 3: Construct fop with desired output format
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);

            // Step 4: Setup JAXP using identity transformer
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(); // identity transformer

            // Step 5: Setup input and output for XSLT transformation
            // Setup input stream
            Source src = new StreamSource(new File("myfile.fo"));

            // Resulting SAX events (the generated FO) must be piped through to FOP
            Result res = new SAXResult(fop.getDefaultHandler());

            // Step 6: Start XSLT transformation and FOP processing
            transformer.transform(src, res);

        } finally {
            // Clean-up
            out.close();
        }
    }
}
