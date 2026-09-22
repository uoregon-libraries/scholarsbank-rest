/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.ctask.general;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.dspace.content.DSpaceObject;
import org.dspace.content.Item;
import org.dspace.content.MetadataValue;
import org.dspace.core.Constants;
import org.dspace.curate.AbstractCurationTask;
import org.dspace.curate.Curator;
import org.dspace.curate.Suspendable;

/**
 * RequiredMetadata task compares item metadata with fields
 * marked as required in submission-forms.xml. The task succeeds if all
 * required fields are present in the item metadata, otherwise it fails.
 * Primarily a curation task demonstrator.
 *
 * @author richardrodgers
 */
@Suspendable
public class AccessibilityRemediation extends AbstractCurationTask {

    protected int status = Curator.CURATE_UNSET;
    protected String result = null;

    /**
     * Perform the curation task upon passed DSO
     *
     * @param dso the DSpace object
     */
    @Override
    public int perform(DSpaceObject dso){
        if (dso instanceof Item) {
            Item item = (Item) dso;
            int count = 0;
            StringBuilder sb = new StringBuilder();
            String handle = item.getHandle();
            if (handle == null) {
                // we are still in workflow - no handle assigned
                handle = "in workflow";
            }
            sb.append("Item: ").append(handle);
            
            List<MetadataValue> metadataValues = itemService.getMetadataByMetadataString(item, "local.accessibility.attestation");
            if (metadataValues != null && !metadataValues.isEmpty()) {
                String attestation = metadataValues.get(0).getValue();
                if ("consultation".equals(attestation)) {
                    sb.append(" requires an accessibility consultation.");
                } else if ("attest".equals(attestation)) {
                    count++;
                    sb.append(" has an accessibility attestation.");
                } else {
                    sb.append(" has an invalid accessibility attestation.");
                }
            } else {
                sb.append(" is missing an accessibility attestation.");
            }
            report(sb.toString());
            setResult(sb.toString());
            
            return (count > 0) ? Curator.CURATE_SUCCESS : Curator.CURATE_FAIL;
        } else {
            setResult("Object skipped");
            return Curator.CURATE_SKIP;
        }
    }
}
