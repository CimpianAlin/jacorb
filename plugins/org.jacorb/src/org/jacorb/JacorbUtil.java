/** 
 * This file is protected by Copyright. 
 * Please refer to the COPYRIGHT file distributed with this source distribution.
 * 
 * This file is part of REDHAWK IDE.
 * 
 * All rights reserved.  This program and the accompanying materials are made available under 
 * the terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 */
package org.jacorb;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.SafeRunner;
import org.omg.CORBA.ORB;

/**
 * @since 3.1
 * 
 */
public final class JacorbUtil {
	private JacorbUtil() {
		
	}

	private static final String CORBA_NAMESPACE = "org.omg.CORBA.";
	
	public static ORB init() {
		JacorbActivator.getDefault().init();
		final ORB [] retVal = new ORB[1];
		SafeRunner.run(new ISafeRunnable() {
			
			@Override
			public void run() throws Exception {
				retVal[0] = org.jacorb.orb.ORB.init();
			}
			
			@Override
			public void handleException(Throwable exception) {
				
			}
		});
		return retVal[0];
	}
	
	/**
	 * @since 3.1
	 */
	public static ORB init(final String[] args, final Properties properties) {
		JacorbActivator.getDefault().init();
		if (properties != null) {
			Map<Object, Object> newElements = new HashMap<Object, Object>();
			for (Entry<Object, Object> entry : properties.entrySet()) {
				Object key = entry.getKey();
				if (key instanceof String && ((String) key).startsWith(CORBA_NAMESPACE)) {
					newElements.put(((String) key).substring(CORBA_NAMESPACE.length()), entry.getValue());
				}
			}
			properties.putAll(newElements);
		}
		JacorbActivator.setupProperties(properties);
		final ORB [] retVal = new ORB[1];
		SafeRunner.run(new ISafeRunnable() {
			
			@Override
			public void run() throws Exception {
				retVal[0] = org.jacorb.orb.ORB.init(args, properties);
			}
			
			@Override
			public void handleException(Throwable exception) {
				
			}
		});
		return retVal[0];
	}

	public static ORB init(final Properties properties) {
		return JacorbUtil.init((String[]) null, properties);
	}
}
