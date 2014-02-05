

<%@include file="/jsp/common.jsp" %>

<h2>Alloy UI TestPage</h2><br/><br/>
<h3>Mainly useful as example of some aui layout/column tags</h3><br/><br/>


<aui:form   name="personalData">
<aui:layout> 
    <aui:column columnWidth="60" first="true" > 
        <aui:input  inlineField="true" label="First Name : " name="firstName"></aui:input> 
        <aui:input  inlineField="true" label="Last Name : " name="lastName"></aui:input>
        <aui:input  inlineField="true" label="Date of Birth: "  name="birthDate"></aui:input>
        <aui:field-wrapper name="gender"> 
            <aui:input checked="true" inlineLabel="right" name="gender" type="radio" value="1" label="male" /> 
            <aui:input inlineLabel="right" name="gender" type="radio" value="2" label="female" /> 
        </aui:field-wrapper>
        
        <aui:select label="Country" name="selectionStyle"> 
            <aui:option selected="india" value="india">
                India
            </aui:option>
            <aui:option selected="us" value="us">
                US
            </aui:option> 
            <aui:option selected="uk" value="uk">
                UK
            </aui:option>  
        </aui:select>
        <br/><br/>
        <aui:fieldset> 
             <aui:input type="checkbox" name="liferay" label="Liferay" value="" > </aui:input>
             <aui:input type="checkbox" name="alfresco" label="Alfresco" value="" > </aui:input>
             <aui:input type="checkbox" name="drupal" label="Drupal" value="" > </aui:input>             
        </aui:fieldset>
    </aui:column> 
    
    <aui:column columnWidth="40" last="true"> 
        <aui:panel collapsible="true" id="educationdetails" label="Education Details">
            <aui:input inlineField="true"
            			maxlength="250"
            			rows="6"
            			label="Edu. Details : "  
            			name="education" 
            			type="textarea"
            			required="true">
            			</aui:input>
        </aui:panel>
        <aui:panel collapsible="true" id="experiencedetails" label="Experience Details">
            <aui:input   inlineField="true" 
            			 rows="4"
            			 cols="5"
            			 mxlength="10"
            			 label="Exp. Details : "  
            			 name="experience" 
            			 type="textarea">
            			 </aui:input>
        </aui:panel> 
    </aui:column> 
</aui:layout>
</aui:form>
<aui:button-row> <aui:button name="saveButton" type="submit" value="save" onClick="javascript:alert('Save?')" /> <aui:button name="cancelButton" type="button" value="cancel" onClick="javascript:alert('Cancel?')" /> </aui:button-row>


<aui:script use="aui-form-validator">

var val = new A.FormValidator({
    boundingBox: form,
    rules: {
        '<portlet:namespace />firstName': {
            required: true
        },
        '<portlet:namespace />lastName': {
            required: true
        }
    },
    strings: {
        required: '<liferay-ui:message key="this-field-is-required" />'
    }
});

</aui:script>
  	

