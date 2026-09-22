/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.app.rest.model.step;

/**
 * Java Bean to expose the section accessibility attestation during in progress submission.
 */
public class AccessibilityAttestation implements SectionData {
    private String attested;

    public String getAttested() {
        return attested;
    }

    public void setAttested(String attested) {
        this.attested = attested;
    }
}
