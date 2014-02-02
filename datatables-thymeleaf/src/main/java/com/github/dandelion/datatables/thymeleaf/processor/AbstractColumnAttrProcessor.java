/*
 * [The "BSD licence"]
 * Copyright (c) 2013-2014 Dandelion
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 3. Neither the name of Dandelion nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.github.dandelion.datatables.thymeleaf.processor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.thymeleaf.Arguments;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.IAttributeNameProcessorMatcher;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.attr.AbstractAttrProcessor;

import com.github.dandelion.datatables.core.configuration.ConfigToken;
import com.github.dandelion.datatables.core.extension.Extension;
import com.github.dandelion.datatables.core.html.HtmlTable;
import com.github.dandelion.datatables.thymeleaf.dialect.DataTablesDialect;

/**
 * Abstract superclass for all processors applied to the {@code th} tag.
 * 
 * @author Thibault Duchateau
 * @since 0.9.0
 */
public abstract class AbstractColumnAttrProcessor extends AbstractAttrProcessor {

	protected Map<ConfigToken<?>, Object> stagingConf;
	protected Map<ConfigToken<?>, Extension> stagingExt;
	protected HtmlTable table;

	public AbstractColumnAttrProcessor(IAttributeNameProcessorMatcher matcher) {
		super(matcher);
	}
	
    @Override
    @SuppressWarnings("unchecked")
    protected ProcessorResult processAttribute(Arguments arguments, Element element, String attributeName) {
    	
    	HttpServletRequest request = ((IWebContext) arguments.getContext()).getHttpServletRequest();
		
		stagingConf = (Map<ConfigToken<?>, Object>) arguments
				.getLocalVariable(DataTablesDialect.INTERNAL_BEAN_COLUMN_LOCAL_CONF);

		stagingExt = (Map<ConfigToken<?>, Extension>) arguments.getLocalVariable(DataTablesDialect.INTERNAL_BEAN_COLUMN_LOCAL_EXT);
		
		table = (HtmlTable) request.getAttribute(DataTablesDialect.INTERNAL_BEAN_TABLE);
		
        doProcessAttribute(arguments, element, attributeName);
        
        // Housekeeping
        element.removeAttribute(attributeName);
        
        return ProcessorResult.ok();
    }

    @Override
    public abstract int getPrecedence();

    /**
     * Process the Attribute
     *
     * @param arguments Thymeleaf arguments
     * @param element Element of the attribute
     * @param attributeName attribute name
     * @return result of process
     */
    protected abstract void doProcessAttribute(Arguments arguments, Element element, String attributeName);
}
