/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.dubbo.common.utils;

import org.junit.Test;

import static org.apache.dubbo.common.utils.Assert.notNull;
import static org.apache.dubbo.common.utils.Assert.notEmptyString;

public class AssertTest {
    @Test(expected = IllegalArgumentException.class)
    public void testNotNull1() throws Exception {
        notNull(null, "null object");
    }

    @Test(expected = IllegalStateException.class)
    public void testNotNull2() throws Exception {
        notNull(null, new IllegalStateException("null object"));
    }

    @Test
    public void testNotNullWhenInputNotNull1() {
        notNull(new Object(),"null object");
    }

    @Test
    public void testNotNullWhenInputNotNull2() {
        notNull(new Object(),new IllegalStateException("null object"));
    }
    @Test(expected = IllegalArgumentException.class)
    public void testNotNullString() {
        notEmptyString(null,"Message can't be null");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotEmptyString() {
        notEmptyString("","Message can't be null or empty");
    }

    @Test
    public void testNotNullNotEmptyString() {
        notEmptyString("abcd","Message can'be null or empty");
    }
}
