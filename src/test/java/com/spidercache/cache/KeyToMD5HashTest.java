package com.spidercache.cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;


public class KeyToMD5HashTest {

	String[] keys = {"http://www.google.com", "http://www.utoledo.edu", "http://www.stackoverflow.com"};
	String[] alteredKeys = {"https://www.google.com", "https://www.utoledo.edu", "https://www.stackoverflow.com"};
	String[] randomKeys = {"-+-J=X@r9y3_-3-$O{¤x2£C`_86v*73Q\\}-3<Ot__@$43#5AWU",
			"xB-5e3_eo8-4__92}_?x-f3---I[-_mR1n3_XP21n_-&--]_RD",
			"4,_mD.j_8_8_05U@n3ocm-n+?Z¤J2_zbf)J.+&-'W_0-g-k96]"};
	String[] hashes;
	
	@Before
	public void initHashes()
	{
		hashes = new String[keys.length];
		for(int i=0; i<hashes.length; i++)
		{
			hashes[i] = CacheUtility.keyToMD5Hash(keys[i]);
		}
	}
	
	@Test
	public void testMD5HashFunction()
	{
		for(int i=0; i<hashes.length; i++)
		{
			assertEquals(hashes[i], CacheUtility.keyToMD5Hash(keys[i]));
			if(hashes[i] == CacheUtility.keyToMD5Hash(alteredKeys[i]))
			{
				fail();
			}
			
			if(hashes[i] == CacheUtility.keyToMD5Hash(randomKeys[i]))
			{
				fail();
			}
		}	
	}
}
