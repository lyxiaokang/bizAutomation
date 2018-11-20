package com.jxrt.util;

import java.io.BufferedWriter;  
import java.io.FileNotFoundException;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.OutputStream;  
import java.io.OutputStreamWriter;  
import java.util.ArrayList;  
import java.util.Enumeration;  
import java.util.List;  
import java.util.Properties;  
  
public class PropertiesHelper extends Properties {  
  
    private static final long serialVersionUID = 1L;  
    
    private List<Object> keyList = new ArrayList<Object>();  

    @Override  
    public synchronized Object put(Object key, Object value) {  
    	int index = keyList.indexOf(key);
    	if (index==-1) {
    		keyList.add(key);
		} 
        return super.put(key, value);  
    }  
      
    @Override  
    public synchronized Object remove(Object key) {  
        this.removeKeyIfExists(key);  
        return super.remove(key);  
    }  
  
    private void removeKeyIfExists(Object key) {  
        keyList.remove(key);  
    }  

    public List<Object> getKeyList() {  
        return keyList;  
    }  
       
    public void store(String path, String charset) {  
        if (path != null && !"".equals(path)) {  
            try {  
                OutputStream os = new FileOutputStream(path);  
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, charset));  
                this.store(bw, null);  
                bw.close();  
            } catch (FileNotFoundException e) {  
                e.printStackTrace();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        } else {  
            throw new RuntimeException("error!");  
        }  
    }  

    @Override  
    public synchronized Enumeration<Object> keys() {  
        return new EnumerationAdapter<Object>(keyList);  
    }  

    private class EnumerationAdapter<T> implements Enumeration<T> {  
        private int index = 0;  
        private final List<T> list;  
        private final boolean isEmpty;  
          
        public EnumerationAdapter(List<T> list) {  
            this.list = list;  
            this.isEmpty = list.isEmpty();  
        }  
          
        public boolean hasMoreElements() {  
            return !isEmpty && index < list.size();  
        }  
  
        public T nextElement() {  
            if (this.hasMoreElements()) {  
                return list.get(index++);  
            }  
            return null;  
        }  
          
    }  
  
} 