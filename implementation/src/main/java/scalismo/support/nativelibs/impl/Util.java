/*
 * Copyright 2016 University of Basel, Graphics and Vision Research Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package scalismo.support.nativelibs.impl;

import scalismo.support.nativelibs.NativeLibraryException;

import java.io.*;
import java.net.URL;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.Arrays;

import static scalismo.support.nativelibs.NativeLibraryBundles.MAJOR_VERSION;
import static scalismo.support.nativelibs.NativeLibraryBundles.MINOR_VERSION;

public class Util {
	private Util() {
	}

	public static File scalismoNativeDir = new File(System.getProperty("user.home")  +File.separator + ".scalismo"
			+ File.separator + "native-libs-" + MAJOR_VERSION + "." + MINOR_VERSION);

	public static File createScalismoNativeDirectory(String prefix, File parent) throws NativeLibraryException {


		Throwable error = null;
		try {
			if (!scalismoNativeDir.exists()) {
				scalismoNativeDir.mkdirs();
			}
		} catch (Throwable t) {
			throw new NativeLibraryException(
					"Unable to create directory for native libs", t);
		}
		return scalismoNativeDir;
	}


	public static byte[] getDigest(URL url) throws NativeLibraryException, IOException {
		byte[] digest = null;

		try {
			BufferedInputStream is = new BufferedInputStream(url.openStream());

			MessageDigest md = MessageDigest.getInstance("MD5");
			DigestInputStream dis = new DigestInputStream(is, md);

			byte[] buffer = new byte[4096 * 512];
			for (int read = is.read(buffer); read >= 0; read = is.read(buffer)) {
				md.update(buffer, 0, read);
			}
			digest = md.digest();
			is.close();
		}

	catch( java.security.NoSuchAlgorithmException e)
	{
		throw new NativeLibraryException("Error while computing MD5 hash for url " +url, e);
	}

	return digest;
}

	public static void copyUrlToFile(URL url, File file) throws IOException, NativeLibraryException {


		BufferedInputStream is = new BufferedInputStream(url.openStream());
		if (file.exists() && Arrays.equals(getDigest(url),getDigest(file.toURI().toURL()))) {
			// nothing to do, we just return
			return;
		}

		// otherwise we copy the file from the jar to the path.
		BufferedOutputStream os = new BufferedOutputStream(
				new FileOutputStream(file));

		byte[] buffer = new byte[4096 * 512];
		for (int read = is.read(buffer); read >= 0; read = is.read(buffer)) {
			os.write(buffer, 0, read);
		}
		is.close();
		os.close();
	}


}
