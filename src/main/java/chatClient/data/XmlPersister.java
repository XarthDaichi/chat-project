/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatClient.data;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import chatClient.data.Data;
import chatProtocol.User;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class XmlPersister {
    private String path;
    private static XmlPersister theInstance;
    public static XmlPersister instance(User user) {
        if (theInstance==null){
            theInstance=new XmlPersister("chat" + user.getNombre() + ".xml");
        }
        return theInstance;
    }

    public XmlPersister(String p) {
        path=p;
    }

    public Data load() throws Exception{
        JAXBContext jaxbContext = JAXBContext.newInstance(Data.class);
        FileInputStream is = new FileInputStream(path);
        //FileInputStream is = new FileInputStream(this.getClass().getResource(path).getFile());
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Data result = (Data) unmarshaller.unmarshal(is);
        is.close();
        return result;
    }

    public void store(Data d)throws Exception{
        JAXBContext jaxbContext = JAXBContext.newInstance(Data.class);
        FileOutputStream os = new FileOutputStream(path);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.marshal(d, os);
        os.flush();
        os.close();
    }
}