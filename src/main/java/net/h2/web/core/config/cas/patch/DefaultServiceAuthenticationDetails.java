

/*

 * Copyright 2011 the original author or authors.

 * 

 * Licensed under the Apache License, Version 2.0 (the "License");

 * you may not use this file except in compliance with the License.

 * You may obtain a copy of the License at

 * 

 *      http://www.apache.org/licenses/LICENSE-2.0

 * 

 * Unless required by applicable law or agreed to in writing, software

 * distributed under the License is distributed on an "AS IS" BASIS,

 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.

 * See the License for the specific language governing permissions and

 * limitations under the License.

 */

package net.h2.web.core.config.cas.patch;

 

import java.util.regex.Pattern;

 

import javax.servlet.http.HttpServletRequest;

 

import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import org.springframework.security.web.util.UrlUtils;

import org.springframework.util.Assert;

 

/**

 * A default implementation of {@link ServiceAuthenticationDetails} that figures

 * out the value for {@link #getServiceUrl()} by inspecting the current

 * {@link HttpServletRequest} and using the current URL minus the artifact and

 * the corresponding value.

 *

 * @author Rob Winch

 */

public class DefaultServiceAuthenticationDetails extends WebAuthenticationDetails implements ServiceAuthenticationDetails {

   private static final long serialVersionUID = 6192409090610517700L;

 

   //~ Instance fields ================================================================================================

 

   private final String serviceUrl;

 

   //~ Constructors ===================================================================================================

 

   /**

     * Creates a new instance

     * @param request

     *            the current {@link HttpServletRequest} to obtain the

     *            {@link #getServiceUrl()} from.

     * @param artifactPattern

     *            the {@link Pattern} that will be used to clean up the query

     *            string from containing the artifact name and value. This can

     *            be created using {@link #createArtifactPattern(String)}.

     */

   public DefaultServiceAuthenticationDetails(HttpServletRequest request, Pattern artifactPattern) {

       super(request);

       final String query = getQueryString(request,artifactPattern);

       this.serviceUrl = UrlUtils.buildFullRequestUrl(request.getScheme(),

               request.getServerName(), request.getServerPort(),

               request.getRequestURI(), query);

   }
   
   
   public DefaultServiceAuthenticationDetails(HttpServletRequest request) {

       super(request);

       Pattern artifactPattern = createArtifactPattern(ServiceProperties.DEFAULT_CAS_ARTIFACT_PARAMETER);
       
       final String query = getQueryString(request,artifactPattern);

       this.serviceUrl = UrlUtils.buildFullRequestUrl(request.getScheme(),

               request.getServerName(), request.getServerPort(),

               request.getRequestURI(), query);

   }

 

   //~ Methods ========================================================================================================

 

   /**

     * Returns the current URL minus the artifact parameter and its value, if present.

     * @see net.ictcert.framework.core.server.casclient.authdetail.patch.authentication.ServiceAuthenticationDetails#getServiceUrl()

     */

   @Override
public String getServiceUrl() {

       return serviceUrl;

   }

 

   @Override

   public int hashCode() {

       final int prime = 31;

       int result = super.hashCode();

       result = prime * result

               + serviceUrl.hashCode();

       return result;

   }

 

   @Override

   public boolean equals(Object obj) {

       if (this == obj) {

           return true;

       }

       if (!super.equals(obj) || !(obj instanceof DefaultServiceAuthenticationDetails)) {

           return false;

       }

       ServiceAuthenticationDetails that = (ServiceAuthenticationDetails) obj;

       return serviceUrl.equals(that.getServiceUrl());

   }

 

   @Override

   public String toString() {

       StringBuilder result = new StringBuilder();

       result.append(super.toString());

       result.append("ServiceUrl: ");

       result.append(serviceUrl);

       return result.toString();

   }

 

   /**

     * If present, removes the artifactParameterName and the corresponding value from the query String.

     * @param request

     * @return the query String minus the artifactParameterName and the corresponding value.

     */

   private String getQueryString(final HttpServletRequest request, final Pattern artifactPattern) {

       final String query = request.getQueryString();

       if(query == null) {

           return null;

       }

       final String result = artifactPattern.matcher(query).replaceFirst("");

       if(result.length() == 0) {

           return null;

       }

       // strip off the trailing & only if the artifact was the first query param

       return result.startsWith("&") ? result.substring(1) : result;

   }

 

   /**

     * Creates a {@link Pattern} that can be passed into the constructor. This

     * allows the {@link Pattern} to be reused for every instance of

     * {@link DefaultServiceAuthenticationDetails}.

     *

     * @param artifactParameterName

     * @return

     */

   static Pattern createArtifactPattern(String artifactParameterName) {

       Assert.hasLength(artifactParameterName);

       return Pattern.compile("&?"+Pattern.quote(artifactParameterName)+"=[^&]*");

   }

}

