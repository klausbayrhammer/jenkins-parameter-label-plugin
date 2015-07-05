package kbayrhammer.parameterlabel;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import hudson.Extension;
import hudson.model.ChoiceParameterDefinition;
import org.apache.commons.lang.StringUtils;
import org.jvnet.localizer.ResourceBundleHolder;
import org.kohsuke.stapler.DataBoundConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kbayrhammer on 05.07.2015.
 */
public class LabeledParameterDefinition extends ChoiceParameterDefinition {

    private final List<LabeledChoices> labeledChoices;

    @DataBoundConstructor
    public LabeledParameterDefinition(String name, String choices, String labels, String description) {
        super(name, choices, description);
        String[] splittedLabels = labels.split(CHOICES_DELIMETER);
        String[] splittedValues = choices.split(CHOICES_DELIMETER);

        List<LabeledChoices> res = new ArrayList<LabeledChoices>();
        for (int i = 0; i < splittedValues.length; i++) {
            res.add(new LabeledChoices(splittedLabels[i], splittedValues[i]));
        }
        labeledChoices = res;
    }

    public String getLabelsText() {
        return StringUtils.join(Lists.transform(labeledChoices, new Function<LabeledChoices, String>() {
            @Override
            public String apply(LabeledChoices labeledChoices) {
                return labeledChoices.getLabel();
            }
        }), "\n");
    }

    public List<LabeledChoices> getLabeledChoices() {
        return labeledChoices;
    }

    public String getLabelAt(int idx) {
        return labeledChoices.get(idx).getLabel();
    }

    public String getValueAt(int idx) {
        return labeledChoices.get(idx).getValue();
    }

    @Extension
    public final static class ParameterDescriptorImpl extends ParameterDescriptor {

        @Override
        public String getDisplayName() {
            return ResourceBundleHolder.get(LabeledParameterDefinition.class).format("DisplayName");
        }

        @Override
        public String getHelpFile() {
            return "/help/parameter/string.html";
        }

    }

    public static final class LabeledChoices {
        private String label;
        private String value;

        public LabeledChoices(String label, String value) {
            this.label = label;
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public String getValue() {
            return value;
        }
    }
}
