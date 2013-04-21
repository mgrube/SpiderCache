package com.spidercache.cache;
import java.io.IOException;


public class BoundedCache <T> extends UnboundedCache <T>{

        private long capacity;
        private final long minimumCapacity;

        public BoundedCache(long capacity, long minimumCapacity)
        {
            super();
            this.capacity = capacity;
            this.minimumCapacity = minimumCapacity;
        }

    public void insert(String key, T entry, long size) throws IOException
    {
        while(capacity - size <  minimumCapacity)
        {
            FrequencyNode lfu = super.frequencies.getLowestFrequency();
            String lfuKey = (String)lfu.getItems().toArray()[0];
            this.remove(lfuKey);
        }
        super.insert(key, entry);
        byKey.get(key).setSize(size);
        capacity = capacity - size;
    }

    public void remove(String key)
	{
		LfuItem<T> item = byKey.get(key);
		if(item != null)
		{
			capacity = capacity + item.getSize();
			super.remove(key);
		}
	}
}
