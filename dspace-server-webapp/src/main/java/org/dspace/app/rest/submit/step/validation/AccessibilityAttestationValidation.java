/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.app.rest.submit.step.validation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dspace.app.rest.model.ErrorRest;
import org.dspace.app.rest.repository.WorkspaceItemRestRepository;
import org.dspace.app.rest.submit.SubmissionService;
import org.dspace.app.util.DCInputsReaderException;
import org.dspace.app.util.SubmissionStepConfig;
import org.dspace.content.InProgressSubmission;
import org.dspace.content.MetadataValue;
import org.dspace.content.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This submission validation checks that the accessibility attestation has been completed
 * for the in-progress submission by verifying the presence of the local.accessibility.attestation metadata.
 *
 * @author DSpace
 */
public class AccessibilityAttestationValidation extends AbstractValidation {

    private static final String ERROR_VALIDATION_ACCESSIBILITY_ATTESTATION_REQUIRED = "error.validation.accessibility-attestation.required";

    private static final Logger log = LogManager.getLogger(AccessibilityAttestationValidation.class);

    private ItemService itemService;

    @Override
    public List<ErrorRest> validate(SubmissionService submissionService, InProgressSubmission obj,
                                    SubmissionStepConfig config) throws DCInputsReaderException, SQLException {

        List<ErrorRest> errors = new ArrayList<>();
        List<MetadataValue> metadataValues = itemService.getMetadataByMetadataString(obj.getItem(),
                "local.accessibility.attestation");
        if (metadataValues == null || metadataValues.isEmpty()) {
            addError(errors, ERROR_VALIDATION_ACCESSIBILITY_ATTESTATION_REQUIRED,
                "/" + WorkspaceItemRestRepository.OPERATION_PATH_SECTIONS + "/" + config.getId());
        }
        return errors;
    }

    public ItemService getItemService() {
        return itemService;
    }

    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

}
