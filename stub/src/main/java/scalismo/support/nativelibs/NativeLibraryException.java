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


/* Implementation note: this class is defined both in the stub and implementation projects,
 * which means that care must be taken to synchronize both in case of changes.
 */
package scalismo.support.nativelibs;

public class NativeLibraryException extends Exception {
    private static final long serialVersionUID = 1L;

    public NativeLibraryException(String msg) {
        super(msg);
    }

    public NativeLibraryException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public NativeLibraryException(Throwable cause) {
        super(cause);
    }

    public static NativeLibraryException wrap (Throwable throwable) {
        if (throwable == null) {
            return null;
        }
        else if (throwable instanceof NativeLibraryException) {
            return  (NativeLibraryException) throwable;
        } else {
            return new NativeLibraryException(throwable);
        }

    }
}