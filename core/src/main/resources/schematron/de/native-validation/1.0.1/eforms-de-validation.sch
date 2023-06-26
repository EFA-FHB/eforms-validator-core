<?xml version="1.0" encoding="UTF-8"?>
<schema xmlns="http://purl.oclc.org/dsdl/schematron" queryBinding="xslt2"
  defaultPhase="eforms-de-phase">

  <title>eForms-DE Schematron Version @eforms-de-schematron.version.full@ compliant with eForms-DE specification @eforms-de.version.full@</title>

  <!-- working on four different UBL Structures -->
  <ns prefix="can"
    uri="urn:oasis:names:specification:ubl:schema:xsd:ContractAwardNotice-2" />
  <ns prefix="cn"
    uri="urn:oasis:names:specification:ubl:schema:xsd:ContractNotice-2" />
  <ns prefix="pin"
    uri="urn:oasis:names:specification:ubl:schema:xsd:PriorInformationNotice-2" />
  <ns prefix="brin"
    uri="http://data.europa.eu/p27/eforms-business-registration-information-notice/1" />
  <!-- And the subordinate namespaces -->
  <ns prefix="cbc"
    uri="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" />
  <ns prefix="cac"
    uri="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" />
  <ns prefix="ext"
    uri="urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2" />
  <ns prefix="efac"
    uri="http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1" />
  <ns prefix="efext" uri="http://data.europa.eu/p27/eforms-ubl-extensions/1" />
  <ns prefix="efbc"
    uri="http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1" />
  <ns prefix="xs" uri="http://www.w3.org/2001/XMLSchema" />

  <phase id="eforms-de-phase">
    <active pattern="global-variable-pattern" />
    <active pattern="technical-sanity-pattern" />
    <active pattern="cardinality-pattern" />
    <active pattern="codelists" />
    <!--<active pattern="codelist-checks"/>-->
  </phase>

  <include href="./common.sch" />
  <include href="./eforms-de-codes.sch" />

  <let name="EFORMS-DE-MAJOR-MINOR-VERSION" value="'1.0'" />

  <let name="EFORMS-DE-ID"
    value="concat('eforms-de-', $EFORMS-DE-MAJOR-MINOR-VERSION)" />
  <let name="SUBTYPES-ALL"
    value="('1', '2', '3', '4', '5', '6', 'E2', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', 'E3', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30', '31', '32', 'E4', '33', '34', '35', '36', '37', '38', '39', '40', 'E5')" />
  <let name="SUBTYPES-BT-06"
    value="('7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', 'E3', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30', '31', '32', 'E4', '33', '34', '35', '36', '37', '38', '39', '40', 'E5')" />
  <let name="SUBTYPES-BT-760"
    value="('29', '30', '31', '32', 'E4', '33', '34', '35', '36', '37', 'E5')" />

  <let name="SUBTYPES-BT-15"
    value="('10', '11', '12', '13', '14', '15', '16', '17', '18', '19', 'E3', '20', '21', '22', '23', '24')" />
  <let name="SUBTYPES-BT-708"
    value="('E1', '1', '2', '3', '4', '5', '6', 'E2', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', 'E3', '20', '21', '22', '23', '24')" />
  <let name="SUBTYPES-BT-97-63-17"
    value="('7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', 'E3', '20', '21', '22', '23', '24')" />
  <let name="SUBTYPES-BT-769"
    value="('E1', '7', '8', '9', '10', '11', '12', '13', '14', '16', '17', '18', '19', 'E3', '20', '21', '22', '23', '24')" />
  <let name="SUBTYPES-BT-771-772"
    value="('7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', 'E3', '20', '21', '23', '24')" />
  <let name="SUBTYPES-BT-726"
    value="('4', '5', '6', 'E2', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', 'E3', '20', '21', '22')" />
  <let name="SUBTYPES-BT-17"
    value="('E1', '10', '11', '15', '16', '17', '23', '24')" />
  <let name="SUBTYPES-BT-97"
    value="('E1', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', 'E3', '20', '21', '22', '23', '24')" />
  <let name="SUBTYPES-BT-63"
    value="('7', '8', '9', '10', '11', '12', '13', '14', '16', '17', '18', '19', 'E3', '20', '21', '22', '23', '24')" />

  <!-- let us name each variable which contains an xpath with suffix NODE (XML lingo for general name XML parts like attribute, element, text, comment,...  -->
  <let name="ROOT-NODE"
    value="(/cn:ContractNotice | /pin:PriorInformationNotice | /can:ContractAwardNotice | /brin:BusinessRegistrationInformationNotice)" />

  <let name="EXTENSION-NODE"
    value="$ROOT-NODE/ext:UBLExtensions/ext:UBLExtension/ext:ExtensionContent/efext:EformsExtension" />

  <let name="NOTICE_RESULT" value="$EXTENSION-NODE/efac:NoticeResult" />

  <let name="SUBTYPE-CODE-NODE"
    value="$EXTENSION-NODE/efac:NoticeSubType/cbc:SubTypeCode" />
  <let name="SUBTYPE"
    value="normalize-space($EXTENSION-NODE/efac:NoticeSubType/cbc:SubTypeCode/text())" />


  <let name="EXTENSION-ORG-NODE"
    value="$ROOT-NODE/ext:UBLExtensions/ext:UBLExtension/ext:ExtensionContent/efext:EformsExtension/efac:Organizations/efac:Organization" />


  <pattern id="technical-sanity-pattern">
    <!-- Rules to check sanity aspects on technical level e.g. is CustomizationID correct  -->

    <rule context="$ROOT-NODE/cbc:CustomizationID">
      <assert id="SR-DE-1" test="text() = $EFORMS-DE-ID" role="error"
          >[SR-DE-1 ]The value <value-of select="."
           /> of <name /> must be equal to the current version (<value-of
          select="$EFORMS-DE-ID" />) of Standards eForms-DE. </assert>
    </rule>


    <rule context="$EXTENSION-NODE">
      <assert id="SR-DE-2" test="efac:NoticeSubType/cbc:SubTypeCode"
        role="error"
        >[SR-DE-2] The element efac:NoticeSubType/cbc:SubTypeCode must exist as child of <name />.</assert>
    </rule>


    <rule context="$SUBTYPE-CODE-NODE">
      <assert id="SR-DE-3" test="($SUBTYPE = $SUBTYPES-ALL)" role="error"
          >[SR-DE-3] SubTypeCode <value-of select="."
           /> is not valid. It must be a value from this list <value-of
          select="$SUBTYPES-ALL" />.</assert>
    </rule>

  </pattern>


  <pattern id="cardinality-pattern">
    <!-- Rules are roughly sorted by typical order of appearance in an instance -->
    <rule context="$ROOT-NODE">

      <assert id="CR-DE-BT-738"
        test="boolean(normalize-space(cbc:RequestedPublicationDate))"
        role="error"
        >[CR-DE-BT-738] cbc:RequestedPublicationDate is mandatory.</assert>

      <assert test="cac:TenderingTerms" id="SR-DE-01"
        >[SR-DE-01] TenderingTerms must exist in all Notice Types</assert>

      <assert id="CR-BT-01-Germany"
        test="exists(cac:TenderingTerms/cac:ProcurementLegislationDocumentReference/cbc:ID)"
        role="error"
        >[CR-BT-01-Germany] cac:ProcurementLegislationDocumentReference/cbc:ID must exist.</assert>
    </rule>

    <rule
      context="$EXTENSION-NODE/efac:Organizations/efac:UltimateBeneficialOwner">

      <assert id="CR-DE-BT-706-UBO"
        test="count(efac:Nationality/cbc:NationalityID) = 1" role="error"
        >[CR-DE-BT-706-UBO] An efac:UltimateBeneficialOwner has to have on efac:Nationality/cbc:NationalityID</assert>
    </rule>

    <!-- rules common to Company and Touchpoint -->
    <rule abstract="true" id="general-org-rules">

      <let name="ADDRESS-NODE" value="cac:PostalAddress" />
      <let name="CONTACT-NODE" value="cac:Contact" />

      <assert id="SR-DE-4" test="count($ADDRESS-NODE) = 1" role="error"
        >[SR-DE-4] Every <name /> has to have one cac:PostalAddress</assert>

      <assert id="SR-DE-7" test="count($ADDRESS-NODE/cac:Country) = 1"
        role="error"
        >[SR-DE-7] Every <name /> has to have one cac:Country</assert>

      <assert id="SR-DE-9" test="count($CONTACT-NODE) = 1" role="error"
        >[SR-DE-9] Every <name /> has to have one cac:Contact</assert>

      <assert id="CR-DE-BT-500"
        test="boolean(normalize-space(cac:PartyName/cbc:Name))" role="error"
        >[CR-DE-BT-5] Every <name /> must have a Name.</assert>

      <assert id="CR-DE-BT-513"
        test="boolean(normalize-space($ADDRESS-NODE/cbc:CityName))" role="error"
        >[CR-DE-BT-513] Every <name /> must have a cbc:CityName.</assert>

      <assert id="CR-DE-BT-512"
        test="boolean(normalize-space($ADDRESS-NODE/cbc:PostalZone))"
        role="error" see="cac:PostalAddress"
        >[CR-DE-BT-512] <name /> Must have a PostalZone.</assert>

      <assert id="CR-DE-BT-507"
        test="boolean(normalize-space($ADDRESS-NODE/cbc:CountrySubentityCode))"
        role="error"
        >[CR-DE-BT-507] Every <name /> must have CountrySubentityCode.</assert>

      <assert id="CR-DE-BT-514"
        test="boolean(normalize-space($ADDRESS-NODE/cac:Country/cbc:IdentificationCode))"
        role="error"
        >[CR-DE-BT-514] Every <name />must have a  cac:Country/cbc:IdentificationCode.</assert>

      <assert id="CR-DE-BT-739" test="count($CONTACT-NODE/cbc:Telefax) le 1"
        role="error"
        >[CR-DE-BT-739]In every <name /> cac:Contact/cbc:Telefax may only occure ones.</assert>

    </rule>
    <!-- Specific rules for Company -->
    <rule context="$EXTENSION-ORG-NODE/efac:Company">

      <let name="PARTY-LEGAL-ENTITY-NODE" value="cac:PartyLegalEntity" />

      <assert id="SR-DE-10" test="count($PARTY-LEGAL-ENTITY-NODE) = 1"
        role="error"
        >[SR-DE-10] Every <name /> has to have one cac:PartyLegalEntity</assert>

      <assert id="CR-DE-BT-506"
        test="boolean(normalize-space(cac:Contact/cbc:ElectronicMail))"
        role="error"
        >[CR-DE-BT-506] Every Buyer (<name />) must have a cac:Contact/cbc:ElectronicMail.</assert>

      <extends rule="general-org-rules" />
    </rule>
    <!--  rules for TouchPoint -->
    <rule context="$EXTENSION-ORG-NODE/efac:TouchPoint">
      <extends rule="general-org-rules" />
    </rule>


    <rule context="$EXTENSION-ORG-NODE/efac:Company/cac:PartyLegalEntity">
      <assert id="CR-DE-BT-501" test="boolean(normalize-space(cbc:CompanyID))"
        role="error" see="cac:PartyLegalEntity"
        >[CR-DE-BT-501] Every <name /> must have a cbc:CompanyID.</assert>
    </rule>


    <rule
      context="($EXTENSION-ORG-NODE/efac:TouchPoint union $EXTENSION-ORG-NODE/efac:Company)/cbc:EndpointID">
      <assert id="CR-DE-BT-509" test="false()" role="error" see="cbc:EndpointID"
        >[CR-DE-BT-509] forbidden.</assert>
    </rule>


    <rule context="$NOTICE_RESULT/efac:SettledContract">
      <assert id="CR-DE-BT-151-Contract" test="count(cbc:URI) = 0" role="error"
        >[CR-DE-BT-151-Contract] cbc:URI forbidden in <name /></assert>
    </rule>


    <rule
      context="$EXTENSION-NODE/efac:NoticeResult/efac:LotResult/efac:ReceivedSubmissionsStatistics">

      <assert id="CR-DE-BT-760-LotResult" test="
          if ($SUBTYPE = $SUBTYPES-BT-760) then
            boolean(normalize-space(efbc:StatisticsCode))
          else
            true()" role="error">efbc:StatisticsCode must exists.</assert>

    </rule>


    <rule context="$ROOT-NODE/cac:ProcurementProject">

      <assert id="SR-DE-11" test="count(cac:RealizedLocation/cac:Address) = 1"
        role="error">Every <name /> has to have cac:Address</assert>

      <assert id="SR-DE-14"
        test="count(cac:RealizedLocation/cac:Address/cac:Country) = 1"
        role="error">Every <name /> has to have cac:Country</assert>

      <assert id="CR-DE-BT-23"
        test="boolean(normalize-space(cbc:ProcurementTypeCode))" role="error"
        >[CR-DE-BT-23] cbc:ProcurementTypeCode must exist as child of <name />.</assert>

      <assert id="CR-DE-BT-21" test="boolean(normalize-space(cbc:Name))"
        role="error"
        >[CR-DE-BT-21] cbc:Name must exist as child of <name />.</assert>

      <!-- commented-out because EU rules contradict the CELEX. In clarification.
      <assert id="CR-DE-BT-24-Procedure"
        test="boolean(normalize-space(cbc:Description))" role="error"
        >cbc:Description must exists.</assert>
      -->

      <assert id="CR-DE-BT-5071-Procedure"
        test="boolean(normalize-space(cac:RealizedLocation/cac:Address/cbc:CountrySubentityCode))"
        role="error"
        >[CR-DE-BT-5071-Procedure] cac:RealizedLocation/cac:Address/cbc:CountrySubentityCode must exist as child of <name />.</assert>

      <assert id="CR-DE-BT-5141-Procedure"
        test="boolean(normalize-space(cac:RealizedLocation/cac:Address/cac:Country/cbc:IdentificationCode))"
        role="error"
        >[CR-DE-BT-5141-Procedure] cac:RealizedLocation/cac:Address/cac:Country/cbc:IdentificationCode must exist as child of <name />.</assert>

    </rule>


    <rule
      context="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']/cac:ProcurementProject">

      <assert id="CR-DE-BT-23-Lot"
        test="boolean(normalize-space(cbc:ProcurementTypeCode[@listName = 'contract-nature']))"
        role="error"
        >[CR-DE-BT-23-Lot] cbc:ProcurementTypeCode must exist as child of <name />.</assert>

      <assert id="CR-DE-BT-21-Lot" test="boolean(normalize-space(cbc:Name))"
        role="error"
        >[CR-DE-BT-21-Lot] cbc:Name must exist as child of <name />.</assert>

      <assert id="CR-DE-BT-06-Lot-1" test="
          if ($SUBTYPE = $SUBTYPES-BT-06) then
            exists(cac:ProcurementAdditionalType)
          else
            true()" role="error"
        >[CR-DE-BT-06-Lot-1] The 'cac:ProcurementAdditionalType' must exist.</assert>

      <assert id="CR-DE-BT-06-Lot" test="
          if ($SUBTYPE = $SUBTYPES-BT-06) then
            (count(cac:ProcurementAdditionalType/cbc:ProcurementTypeCode[@listName = 'strategic-procurement']) ge 1)
          else
            true()" role="error"
        >[CR-DE-BT-06-Lot] The 'cbc:ProcurementTypeCode' element with a 'listName' attribute value of 'strategic-procurement' must exist within the 'cac:ProcurementAdditionalType'.
      </assert>
    </rule>


    <rule
      context="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Part']/cac:ProcurementProject">

      <assert id="CR-DE-BT-23-Part"
        test="boolean(normalize-space(cbc:ProcurementTypeCode[@listName = 'contract-nature']))"
        role="error"
        >[CR-DE-BT-23-Part] cbc:ProcurementTypeCode must exist as child of <name />.</assert>

      <assert id="CR-DE-BT-21-Part" test="boolean(normalize-space(cbc:Name))"
        role="error"
        >[CR-DE-BT-23-Part] cbc:Name must exist as child of <name />.</assert>
    </rule>


    <!-- following two rules CR-DE-BT-708-Part and CR-DE-BT-708-Lot may be combined into single one -->
    <rule
      context="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Part']/cac:TenderingTerms/cac:CallForTendersDocumentReference">
      <assert id="CR-DE-BT-708-Part" test="
          if ($SUBTYPE = $SUBTYPES-BT-708) then
            (count(cbc:LanguageID) ge 1)
          else
            true()" role="error"
        >[CR-DE-BT-708-Part] cbc:LanguageID must exist.</assert>
    </rule>

    <rule
      context="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']/cac:TenderingTerms/cac:CallForTendersDocumentReference">
      <assert id="CR-DE-BT-708-Lot" test="
          if ($SUBTYPE = $SUBTYPES-BT-708) then
            (count(cbc:LanguageID) ge 1)
          else
            true()" role="error"
        >[CR-DE-BT-708-Lot] cbc:LanguageID must exist.</assert>
    </rule>

    <rule
      context="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot' or cbc:ID/@schemeName = 'Part']/cac:ProcurementProject/cac:RealizedLocation/cac:Address">
      <assert id="SR-DE-15" test="count(cac:Country) = 1" role="error"
        >Every <name /> has to have cac:Country</assert>

      <assert id="CR-DE-BT-5141"
        test="boolean(normalize-space(cac:Country/cbc:IdentificationCode))"
        role="error">[CR-DE-BT-5141] cbc:IdentificationCode must exist.</assert>

      <assert id="CR-DE-BT-5071"
        test="boolean(normalize-space(cbc:CountrySubentityCode))" role="error"
        >[CR-DE-BT-5071] cbc:CountrySubentityCode must exist.</assert>
    </rule>


    <rule
      context="$EXTENSION-NODE/efac:NoticeResult/efac:GroupFramework/efbc:GroupFrameworkMaximumValueAmount">
      <assert id="CR-DE-BT-156-NoticeResult" test="false()" role="error"
        >[CR-DE-BT-156-NoticeResult] efbc:GroupFrameworkMaximumValueAmount forbidden.</assert>
    </rule>

    <rule
      context="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'LotsGroup']/cac:TenderingProcess/cac:FrameworkAgreement/cbc:EstimatedMaximumValueAmount">
      <assert id="CR-DE-BT-157" test="false()" role="error"
        >[CR-DE-BT-157] <name /> is forbidden.</assert>
    </rule>



    <rule
      context="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']/cac:TenderingTerms/cac:TendererQualificationRequest[not(cbc:CompanyLegalFormCode)]">
      <assert id="SR-DE-20" test="
          if ($SUBTYPE = $SUBTYPES-BT-771-772) then
            (count(cac:SpecificTendererRequirement[not(cbc:TendererRequirementTypeCode[@listName = 'reserved-procurement'])]) = 1)
          else
            true()" role="error"
        >Every <name /> has to have one cac:SpecificTendererRequirement</assert>
    </rule>


    <rule
      context="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']/cac:TenderingTerms/cac:TendererQualificationRequest[not(cbc:CompanyLegalFormCode)]/cac:SpecificTendererRequirement[not(cbc:TendererRequirementTypeCode[@listName = 'reserved-procurement'])]">
      <assert id="CR-DE-BT-771-Lot" test="
          if ($SUBTYPE = $SUBTYPES-BT-771-772) then
            boolean(normalize-space(cbc:TendererRequirementTypeCode[@listName = 'missing-info-submission']))
          else
            true()" role="error"
        >[CR-DE-BT-771-Lot] cbc:TendererRequirementTypeCode must exist.</assert>

      <assert id="CR-DE-BT-772-Lot" test="
          if ($SUBTYPE = $SUBTYPES-BT-771-772) then
            boolean(normalize-space(cbc:Description))
          else
            true()" flag="fatal"
        >[CR-DE-BT-772-Lot] cbc:Description must exist.</assert>
    </rule>


    <rule
      context="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']/cac:TenderingTerms">

      <assert id="CR-DE-BT-97-Lot" test="
          if ($SUBTYPE = $SUBTYPES-BT-97) then
            exists(cac:Language/cbc:ID)
          else
            true()" role="error"
          >[CR-DE-BT-97-Lot] cac:Language/cbc:ID must exist for subtypes <value-of
          select="$SUBTYPES-BT-97" />.</assert>

      <assert id="CR-DE-BT-15-Lot" test="
          if ($SUBTYPE = $SUBTYPES-BT-15) then
            (count(cac:CallForTendersDocumentReference[not(cbc:DocumentType/text() = 'restricted-document')]/cac:Attachment/cac:ExternalReference/cbc:URI) ge 1)
          else
            true()" role="error"
        >[CR-DE-BT-15-Lot] cbc:URI must exist.</assert>
    </rule>


    <rule
      context="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Part']/cac:TenderingTerms">
      <assert id="CR-DE-BT-15-Part" test="
          if ($SUBTYPE = $SUBTYPES-BT-15) then
            (count(cac:CallForTendersDocumentReference[not(cbc:DocumentType/text() = 'restricted-document')]/cac:Attachment/cac:ExternalReference/cbc:URI) ge 1)
          else
            true()" role="error"
        >[CR-DE-BT-15-Part] cbc:URI must exist.</assert>
    </rule>

    <rule
      context="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Part']">

      <assert id="CR-DE-BT-726-Part" test="
          if ($SUBTYPE-CODE-NODE = $SUBTYPES-BT-726) then
            (boolean(normalize-space(cac:ProcurementProject/cbc:SMESuitableIndicator)))
          else
            true()" role="error"
        >[CR-DE-BT-726-Part] cbc:SMESuitableIndicator must exist.</assert>

    </rule>


    <rule
      context="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'LotsGroup']">

      <assert id="CR-DE-BT-21-LotsGroup"
        test="boolean(normalize-space(cac:ProcurementProject/cbc:Name))"
        role="error"
        >[CR-DE-BT-21-LotsGroup] cac:ProcurementProject/cbc:Name must exist.</assert>

      <!-- commented-out because EU rules contradict the CELEX. In clarification.
      <assert id="CR-DE-BT-24-LotsGroup"
        test="boolean(normalize-space(cac:ProcurementProject/cbc:Description))"
        role="error">[CR-DE-BT-24-LotsGroup] cac:ProcurementProject/cbc:Description must exists.</assert>
        -->

      <assert id="CR-DE-BT-726-LotsGroup" test="
          if ($SUBTYPE-CODE-NODE = $SUBTYPES-BT-726) then
            (boolean(normalize-space(cac:ProcurementProject/cbc:SMESuitableIndicator)))
          else
            true()" role="error"
        >[CR-DE-BT-726-LotsGroup] cac:ProcurementProject/cbc:SMESuitableIndicator must exist.</assert>

    </rule>


    <rule
      context="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']">

      <assert id="CR-DE-BT-63-Lot" test="
          if ($SUBTYPE = $SUBTYPES-BT-63) then
            boolean(normalize-space(cac:TenderingTerms/cbc:VariantConstraintCode))
          else
            true()" flag="fatal">[CR-DE-BT-63-Lot](<value-of
          select="$SUBTYPE"
           />) cbc:VariantConstraintCode must exist in subtypes <value-of
          select="$SUBTYPES-BT-63" /></assert>

      <assert id="CR-DE-BT-17-Lot" test="
          if ($SUBTYPE = $SUBTYPES-BT-17) then
            boolean(normalize-space(cac:TenderingProcess/cbc:SubmissionMethodCode[@listName = 'esubmission']))
          else
            true()" flag="fatal">[CR-DE-BT-17-Lot](<value-of
          select="$SUBTYPE"
           />) cbc:SubmissionMethodCode must exist in subtypes <value-of
          select="$SUBTYPES-BT-17" />.</assert>

      <assert id="CR-DE-BT-769-Lot" test="
          if ($SUBTYPE = $SUBTYPES-BT-769) then
            boolean(normalize-space(cac:TenderingTerms/cbc:MultipleTendersCode))
          else
            true()" role="error">[CR-DE-BT-769-Lot](<value-of
          select="$SUBTYPE"
           />)cbc:MultipleTendrersCode must exist in subtypes <value-of
          select="$SUBTYPES-BT-769" /></assert>

      <assert id="CR-DE-BT-726-Lot" test="
          if ($SUBTYPE-CODE-NODE = $SUBTYPES-BT-726) then
            (boolean(normalize-space(cac:ProcurementProject/cbc:SMESuitableIndicator)))
          else
            true()" role="error"
        >[CR-DE-BT-726-Lot] cbc:SMESuitableIndicator must exist.</assert>

    </rule>

    <!-- seems to be redundant to EU SDK e.g rule|text|BR-BT-00191-0036">BT-191-Tender is forbidden for a notice with subtype 29</entry> -->
    <rule context="$NOTICE_RESULT/efac:LotTender/efac:Origin/efbc:AreaCode">
      <assert id="CR-DE-BT-191" test="false()" role="error"
        >[CR-DE-BT-191 ]efbc:AreaCode forbidden.</assert>
    </rule>

    <rule
      context="$ROOT-NODE/cac:TenderingTerms/cac:LotDistribution/cac:LotsGroup/cbc:LotsGroupID">
      <assert id="CR-DE-BT-330-Procedure" test="false()" role="error"
        >[CR-DE-BT-330-Procedure] cbc:LotsGroupID forbidden.</assert>
    </rule>


    <rule
      context="$ROOT-NODE/cac:TenderingTerms/cac:LotDistribution/cac:LotsGroup/cac:ProcurementProjectLotReference/cbc:ID[@schemeName = 'Lot']">
      <assert id="CR-DE-BT-1375-Procedure" test="false()" role="error"
        >[CR-DE-BT-1375-Procedure] cbc:ID forbidden.</assert>
    </rule>


    <rule
      context="$EXTENSION-NODE/efac:NoticeResult/efac:GroupFramework/efac:TenderLot/cbc:ID">
      <assert id="CR-DE-BT-556-NoticeResult" test="false()" role="error"
        >[CR-DE-BT-556-NoticeResult] cbc:ID forbidden.</assert>
    </rule>

  </pattern>

</schema>
