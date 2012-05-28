package com.spidercache.queue;

import java.io.Serializable;

public class TestObject implements Serializable {


		private static final long serialVersionUID = 1L;

		String myData;

		public TestObject(String myData) {
			this.myData = myData;
		}

		public String get() {
			return myData;
		}
}
