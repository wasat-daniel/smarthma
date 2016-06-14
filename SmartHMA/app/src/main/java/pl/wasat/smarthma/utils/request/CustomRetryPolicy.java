/*
 * Copyright (c) 2016.  SmartHMA ESA
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

package pl.wasat.smarthma.utils.request;

import android.util.Log;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.retry.DefaultRetryPolicy;

/**
 * Created by Daniel on 2016-04-22.
 * This file is a part of module SmartHMA project.
 */
class CustomRetryPolicy extends DefaultRetryPolicy {

    @Override
    public long getDelayBeforeRetry() {
        return 15L * 1000;
    }

    @Override
    public void retry(SpiceException e) {
        Log.i("RETRY_POLICY", e.toString());
        super.retry(e);
    }
}
