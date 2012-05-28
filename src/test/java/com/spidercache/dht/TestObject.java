package com.spidercache.dht;

import java.io.Serializable;

public class TestObject implements Serializable {


		private static final long serialVersionUID = 1L;

		String myData;

		public TestObject(String myData) {
			this.myData = myData;
		}

		@Override
		public String toString() {
			return "TestObject [myData=" + myData + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((myData == null) ? 0 : myData.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TestObject other = (TestObject) obj;
			if (myData == null) {
				if (other.myData != null)
					return false;
			} else if (!myData.equals(other.myData))
				return false;
			return true;
		}

		public String get() {
			return myData;
		}
}
