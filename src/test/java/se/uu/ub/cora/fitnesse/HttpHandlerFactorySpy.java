/*
 * Copyright 2017 Uppsala University Library
 *
 * This file is part of Cora.
 *
 *     Cora is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Cora is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Cora.  If not, see <http://www.gnu.org/licenses/>.
 */

package se.uu.ub.cora.fitnesse;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import se.uu.ub.cora.httphandler.HttpHandler;
import se.uu.ub.cora.httphandler.HttpMultiPartUploader;

public class HttpHandlerFactorySpy implements HttpHandlerFactory {

	public String urlString;
	private boolean factorValid = true;

	@Override
	public HttpHandler factorHttpHandler(String urlString) {
		this.urlString = urlString;
		try {
			URL url = new URL(urlString);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			if (factorValid) {
				return HttpHandlerSpy.usingURLConnection(urlConnection);
			}
			return HttpHandlerInvalidSpy.usingURLConnection(urlConnection);

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public HttpMultiPartUploader factorHttpMultiPartUploader(String urlString) {
		// TODO Auto-generated method stub
		return null;
	}

	public void changeFactoryToFactorInvalidHttpHandlers() {
		factorValid = false;
	}

}
