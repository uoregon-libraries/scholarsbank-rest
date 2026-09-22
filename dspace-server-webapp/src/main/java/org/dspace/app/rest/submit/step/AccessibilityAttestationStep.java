/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.app.rest.submit.step;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import org.dspace.app.rest.model.MetadataValueRest;
import org.dspace.app.rest.model.patch.Operation;
import org.dspace.app.rest.model.step.AccessibilityAttestation;
import org.dspace.app.rest.submit.AbstractProcessingStep;
import org.dspace.app.rest.submit.DataProcessingStep;
import org.dspace.app.rest.submit.SubmissionService;
import org.dspace.app.rest.submit.factory.PatchOperationFactory;
import org.dspace.app.rest.submit.factory.impl.PatchOperation;
import org.dspace.app.util.SubmissionStepConfig;
import org.dspace.content.InProgressSubmission;
import org.dspace.content.Item;
import org.dspace.content.MetadataValue;
import org.dspace.core.Context;

/**
 * AccessibilithAttestation step for DSpace Spring Rest. Expose information about
 * the 
 */
public class AccessibilityAttestationStep extends AbstractProcessingStep {

    @Override
    public AccessibilityAttestation getData(SubmissionService submissionService, InProgressSubmission obj,
            SubmissionStepConfig config) throws Exception {
        AccessibilityAttestation data = new AccessibilityAttestation();
        Item item = obj.getItem();
        List<MetadataValue> metadataValues = itemService.getMetadataByMetadataString(item, "local.accessibility.attestation");
        if (metadataValues != null && !metadataValues.isEmpty()) {
            data.setAttested(metadataValues.get(0).getValue());
        }
        return data;
    }

    @Override
    public void doPatchProcessing(Context context, HttpServletRequest currentRequest, InProgressSubmission source,
            Operation op, SubmissionStepConfig stepConf) throws Exception {
        PatchOperation<MetadataValueRest> patchOperation = new PatchOperationFactory()
                .instanceOf(ACCESSIBILITY_ATTESTATION_STEP_METADATA_OPERATION_ENTRY, op.getOp());
        patchOperation.perform(context, currentRequest, source, op);

    }
}