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
    <active pattern="conditional-mandatory" />
    <!--<active pattern="codelist-checks"/>-->
  </phase>

  <include href="./common.sch" />
  <include href="./eforms-de-codes.sch" />
  <include href="./eforms-de-conditional-mandatory.sch" />

  <let name="EFORMS-DE-MAJOR-MINOR-VERSION" value="'1.1'" />

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

<!-- "1", "2", "3", "4", "5", "6", "23", "24", "25", "26", "27", "28", "32", "33", "34", "35", "36", "37", "CEI", "T01", "T02", "X01", "X02" -->
  <let name="SUBTYPES-BT-717"
    value="('7', '8', '9', '10', '11', '12', '13', '14', '16', '17', '18', '19', 'E3', '20', '21', '22', '29', '30', '31','E4', '38', '39', '40')" />

  <let name="SUBTYPES-BT-105"
    value="('7', '8', '9', '10', '11', '12', '13', '14', '16', '17', '18', '19', 'E3', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30', '31', '32', 'E4', '33', '34', '35', '36', '37')" />

  <let name="SUBTYPES-BT-165"
    value="('25', '26', '27', '28', '29', '30', '31', '32', 'E4', '33', '34', '35', '36', '37')" />

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

  <let name="EXTENSION-ORG-NODE-PARENT"
    value="$ROOT-NODE/ext:UBLExtensions/ext:UBLExtension/ext:ExtensionContent/efext:EformsExtension/efac:Organizations" />
  <let name="EXTENSION-ORG-NODE"
    value="$EXTENSION-ORG-NODE-PARENT/efac:Organization" />


  <!-- 
    Rules to check sanity aspects on technical level e.g. is CustomizationID correct 
    SR = Sanitycheck Rule
     -->
  <pattern id="technical-sanity-pattern">

    <!-- Verifying the correct CustomizationID is used, e.g. eforms-de-1.0 instead of eforms-sdk-1.5 -->
    <rule context="$ROOT-NODE/cbc:CustomizationID">
      <assert id="SR-DE-1" test="text() = $EFORMS-DE-ID" role="error">[SR-DE-1 ]The value <value-of select="." /> of <name /> must be equal to the current version (<value-of select="$EFORMS-DE-ID" />) of the eForms-DE Standard. </assert>
    </rule>

    <!-- Verifying that SubTypeCode (notice subtype identifier e.g. 1-40 or E2-E4) is present to be used in following rules  -->
    <rule context="$EXTENSION-NODE">
      <assert id="SR-DE-2" test="efac:NoticeSubType/cbc:SubTypeCode" role="error">[SR-DE-2] The element efac:NoticeSubType/cbc:SubTypeCode must exist as child of <name />.</assert>
    </rule>

    <!-- Verifying that SubTypeCode is in list of expected SubTypeCodes, as seen in SUBTYPES-ALL variable  -->
    <rule context="$SUBTYPE-CODE-NODE">
      <assert id="SR-DE-3" test="($SUBTYPE = $SUBTYPES-ALL)" role="error">[SR-DE-3] SubTypeCode <value-of select="." /> is not valid. It must be a value from this list <value-of select="$SUBTYPES-ALL" />.</assert>
    </rule>

  </pattern>


  <!-- 
  Rules are roughly sorted by typical order of appearance in an instance 
  CR = Cardinalitycheck Rule
  -->
  <pattern id="cardinality-pattern">

    <!-- bt-165 mandatory in  25-37 strictly again implemented as CM

BT-165-Organization-Company is mandatory for a notice with subtype 30 under the condition: the Organization is a Winner (i.e. an organization (OPT-200-Organization-Company) identified as a main contractor (OPT-300-Tenderer) or a subcontractor
 (OPT-301-Tenderer-SubCont)

 within a tendering party (OPT-210-Tenderer) that submitted a tender (OPT-310-Tender), which (OPT-321-Tender) led to a contract (BT-3202-Contract))
-->

    <rule context="$ROOT-NODE">

      <assert id="CR-DE-BT-738" test="boolean(normalize-space(cbc:RequestedPublicationDate))" role="error">[CR-DE-BT-738] cbc:RequestedPublicationDate must exist in <name /></assert>

      <assert id="SR-DE-01" test="cac:TenderingTerms" role="error">[SR-DE-01] TenderingTerms must exist in all Notice Types</assert>

      <assert id="CR-BT-01-Germany" test="exists(cac:TenderingTerms/cac:ProcurementLegislationDocumentReference/cbc:ID)" role="error">[CR-BT-01-Germany] cac:TenderingTerms/cac:ProcurementLegislationDocumentReference/cbc:ID must exist in <name />.</assert>
      <!-- BT-105 is /*/cac:TenderingProcess/cbc:ProcedureCode 
in ted already m for "10", "11", "16", "17", "18", "23", "24", "25", "26", "27", "28", "29", "30", "31", "36", "37", "CEI", "T01", "T02" 
we need to add 7,8,9,12,13,14, 19,20,21,22,32,E4,33,34,35
-->
      <assert id="CR-DE-BT-105" test="
          if ($SUBTYPE = $SUBTYPES-BT-105) then
            exists(cac:TenderingProcess/cbc:ProcedureCode)
          else
            true()" role="error">[CR-BT-105] cac:TenderingProcess/cbc:ProcedureCode must exist in subtype=<value-of select="$SUBTYPE" />.</assert>
    </rule>

    <!-- rules common to Company and Touchpoint -->
    <rule abstract="true" id="general-org-rules">

      <let name="ADDRESS-NODE" value="cac:PostalAddress" />
      <let name="CONTACT-NODE" value="cac:Contact" />

      <assert id="SR-DE-4" test="count($ADDRESS-NODE) = 1" role="error">[SR-DE-4] Every <name /> must have one cac:PostalAddress</assert>

      <assert id="SR-DE-7" test="count($ADDRESS-NODE/cac:Country) = 1" role="error">[SR-DE-7] Every <name /> must have one cac:Country</assert>

      <assert id="SR-DE-9" test="count($CONTACT-NODE) = 1" role="error">[SR-DE-9] Every <name /> must have one cac:Contact</assert>

      <assert id="CR-DE-BT-500" test="boolean(normalize-space(cac:PartyName/cbc:Name))" role="error">[CR-DE-BT-5] Every <name /> must have a Name.</assert>

      <assert id="CR-DE-BT-513" test="boolean(normalize-space($ADDRESS-NODE/cbc:CityName))" role="error">[CR-DE-BT-513] Every <name /> must have a cbc:CityName.</assert>

      <assert id="CR-DE-BT-512" test="boolean(normalize-space($ADDRESS-NODE/cbc:PostalZone))" role="error" see="cac:PostalAddress">[CR-DE-BT-512] Every <name /> must have a PostalZone.</assert>

      <assert id="CR-DE-BT-514" test="boolean(normalize-space($ADDRESS-NODE/cac:Country/cbc:IdentificationCode))" role="error">[CR-DE-BT-514] Every <name /> must have a cac:Country/cbc:IdentificationCode.</assert>

      <assert id="CR-DE-BT-739" test="count($CONTACT-NODE/cbc:Telefax) le 1" role="error">[CR-DE-BT-739]In every <name /> cac:Contact/cbc:Telefax may only occure ones.</assert>

    </rule>



    <!-- Specific rules for Company -->
    <rule context="$EXTENSION-ORG-NODE/efac:Company">

      <let name="PARTY-LEGAL-ENTITY-NODE" value="cac:PartyLegalEntity" />

      <assert id="SR-DE-10" test="exists($PARTY-LEGAL-ENTITY-NODE)" role="error">[SR-DE-10] Every <name /> has to have at least one cac:PartyLegalEntity</assert>

      <assert id="CR-DE-BT-506" test="boolean(normalize-space(cac:Contact/cbc:ElectronicMail))" role="error">[CR-DE-BT-506] Every Buyer (<name />) must have a cac:Contact/cbc:ElectronicMail.</assert>

      <extends rule="general-org-rules" />
    </rule>

    <!--  rules for TouchPoint -->
    <rule context="$EXTENSION-ORG-NODE/efac:TouchPoint">
      <extends rule="general-org-rules" />
    </rule>

    <rule context="$EXTENSION-ORG-NODE/efac:Company/cac:PartyLegalEntity">
      <assert id="CR-DE-BT-501" test="boolean(normalize-space(cbc:CompanyID))" role="error" see="cac:PartyLegalEntity">[CR-DE-BT-501] Every <name /> must have a cbc:CompanyID.</assert>
    </rule>

    <rule
      context="($EXTENSION-ORG-NODE/efac:TouchPoint union $EXTENSION-ORG-NODE/efac:Company)/cbc:EndpointID">
      <assert id="CR-DE-BT-509" test="false()" role="error" see="cbc:EndpointID">[CR-DE-BT-509] cbc:EndpointID is forbidden in Company and Touchpoint.</assert>
    </rule>

<!-- Match on level PartyIdentifcation in order to avoid shadowing with rule on company -->
    <rule
      context="$EXTENSION-ORG-NODE/efac:Company[cac:PartyIdentification/cbc:ID = (//efac:TenderingParty/efac:Tenderer/cbc:ID, //efac:TenderingParty/efac:Subcontractor/cbc:ID)]/cac:PartyIdentification"
      role="error">
      
      <assert id="CR-DE-BT-165" role="error" test="../efbc:CompanySizeCode[$SUBTYPE = $SUBTYPES-BT-165]">[CR-DE-BT-165] If this company (<value-of select="cbc:ID" />) is a winner, BT-165 (Winner Size) must exist.</assert>

    </rule>

    <rule context="$NOTICE_RESULT/efac:SettledContract">
      <assert id="CR-DE-BT-151-Contract" test="count(cbc:URI) = 0" role="error">[CR-DE-BT-151-Contract] cbc:URI forbidden in <name /></assert>
    </rule>


    <!-- sanity rule to check for existence of parent node of BT-760 -->
    <rule context="$EXTENSION-NODE/efac:NoticeResult/efac:LotResult">
      <assert id="SR-DE-22" test="
          if ($SUBTYPE = $SUBTYPES-BT-760) then
            (count(efac:ReceivedSubmissionsStatistics) >= 1)
          else
            true()" role="error">[SE-DE-22] efac:ReceivedSubmissionsStatistics must exist in <name /> at least once</assert>
    </rule>

    <rule
      context="$EXTENSION-NODE/efac:NoticeResult/efac:LotResult/efac:ReceivedSubmissionsStatistics">
      <assert id="CR-DE-BT-760-LotResult" test="
          if ($SUBTYPE = $SUBTYPES-BT-760) then
            boolean(normalize-space(efbc:StatisticsCode))
          else
            true()" role="error">[CR-DE-BT-760-LotResult] efbc:StatisticsCode must exist in <name />.</assert>

    </rule>


    <rule context="$ROOT-NODE/cac:ProcurementProject">


      <assert id="SR-DE-14" test="exists(cac:RealizedLocation)" role="error">[SR-DE-14] Every <name /> must have cac:Country</assert>

      <assert id="CR-DE-BT-23" test="boolean(normalize-space(cbc:ProcurementTypeCode))" role="error">[CR-DE-BT-23] <name /> must have  cbc:ProcurementTypeCode.</assert>

      <assert id="CR-DE-BT-21" test="boolean(normalize-space(cbc:Name))" role="error">[CR-DE-BT-21] <name /> must have one cbc:Name.</assert>

    </rule>

    <rule context="$ROOT-NODE/cac:ProcurementProject/cac:RealizedLocation">
      <assert id="SR-DE-11" test="exists(cac:Address)" role="error">[SR-DE-11] Every <name /> must have cac:Address</assert>

      <assert id="SR-DE-11-a" test="exists(cac:Address/cac:Country)" role="error">[SR-DE-14] Every <name /> must have cac:Country</assert>

    </rule>




    <rule
      context="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']/cac:ProcurementProject">

      <assert id="CR-DE-BT-23-Lot" test="boolean(normalize-space(cbc:ProcurementTypeCode[@listName = 'contract-nature']))" role="error">[CR-DE-BT-23-Lot] cbc:ProcurementTypeCode must exist as child of <name />.</assert>

      <assert id="CR-DE-BT-21-Lot" test="boolean(normalize-space(cbc:Name))" role="error">[CR-DE-BT-21-Lot] cbc:Name must exist as child of <name />.</assert>

      <!-- sanity rule to check if parent node of ProcurementTypeCode for BT-06 exists 
      <assert id="SR-DE-25" test="
          if ($SUBTYPE = $SUBTYPES-BT-06) then
            boolean(cac:ProcurementAdditionalType)
          else
            true()" role="error">[SR-DE-25] The 'cac:ProcurementAdditionalType' must exist.</assert>
-->

      <!-- rules to check BR-DE-21 for BT-06 -->
      <assert id="BR-DE-21-A" test="
          if ($SUBTYPE = $SUBTYPES-BT-06) then
            (count(cac:ProcurementAdditionalType/cbc:ProcurementTypeCode[@listName = 'strategic-procurement']) ge 1)
          else
            true()" role="error">[BR-DE-21-A] The 'cac:ProcurementAdditionalType/cbc:ProcurementTypeCode' with listName = 'strategic-procurement' must exist at least once under cac:ProcurementProject.</assert>

      <assert id="BR-DE-21-B" test="
          if ($SUBTYPE = $SUBTYPES-BT-06) then
            (count(cac:ProcurementAdditionalType/cbc:ProcurementTypeCode[@listName = 'strategic-procurement']) le 3)
          else
            true()" role="error">[BR-DE-21-B] The 'cac:ProcurementAdditionalType/cbc:ProcurementTypeCode' with listName = 'strategic-procurement' is allowed at most 3 times under cac:ProcurementProject.</assert>

      <!-- rule to check BR-DE-22 for BT-06 -->
      <assert id="BR-DE-22" test="
          if ($SUBTYPE = $SUBTYPES-BT-06) then
            (count(cac:ProcurementAdditionalType/cbc:ProcurementTypeCode[@listName = 'strategic-procurement']) = count(distinct-values(cac:ProcurementAdditionalType/cbc:ProcurementTypeCode[@listName = 'strategic-procurement'])))
          else
            true()" role="error">[BR-DE-22] Each code in 'cac:ProcurementAdditionalType/cbc:ProcurementTypeCode' with listName = 'strategic-procurement' is only allowed once.</assert>


    </rule>


    <rule
      context="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Part']/cac:ProcurementProject">

      <assert id="CR-DE-BT-23-Part" test="boolean(normalize-space(cbc:ProcurementTypeCode[@listName = 'contract-nature']))" role="error">[CR-DE-BT-23-Part] cbc:ProcurementTypeCode must exist as child of <name />.</assert>

      <assert id="CR-DE-BT-21-Part" test="boolean(normalize-space(cbc:Name))" role="error">[CR-DE-BT-23-Part] cbc:Name must exist as child of <name />.</assert>
    </rule>


    <rule
      context="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot' or cbc:ID/@schemeName = 'Part']/cac:ProcurementProject/cac:RealizedLocation/cac:Address">
      <assert id="SR-DE-15" test="count(cac:Country) = 1" role="error">[SR-DE-15] Every <name /> has to have cac:Country</assert>

      <assert id="CR-DE-BT-5141" test="boolean(normalize-space(cac:Country/cbc:IdentificationCode))" role="error">[CR-DE-BT-5141] cbc:IdentificationCode must exist.</assert>

      <!-- BT-5071 conditional mandatory in 1.1 - see BR-DE-27 
      <assert id="CR-DE-BT-5071" test="boolean(normalize-space(cbc:CountrySubentityCode))" role="error">[CR-DE-BT-5071] cbc:CountrySubentityCode must exist.</assert>
      -->
    </rule>


    <rule
      context="$EXTENSION-NODE/efac:NoticeResult/efac:GroupFramework/efbc:GroupFrameworkMaximumValueAmount">
      <assert id="CR-DE-BT-156-NoticeResult" test="false()" role="error">[CR-DE-BT-156-NoticeResult] efbc:GroupFrameworkMaximumValueAmount forbidden.</assert>
    </rule>

    <rule
      context="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'LotsGroup']/cac:TenderingProcess/cac:FrameworkAgreement/cbc:EstimatedMaximumValueAmount">
      <assert id="CR-DE-BT-157" test="false()" role="error">[CR-DE-BT-157] <name /> is forbidden.</assert>
    </rule>

    <rule
      context="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']/cac:TenderingTerms">

      <assert id="CR-DE-BT-771-Lot" test="
          if ($SUBTYPE = $SUBTYPES-BT-771-772) then
            (count(cac:TendererQualificationRequest[not(cbc:CompanyLegalFormCode)]/cac:SpecificTendererRequirement[(cbc:TendererRequirementTypeCode[@listName = 'missing-info-submission'])]) = 1)
          else
            true()" role="error">[CR-DE-BT-771-Lot] Exactly one cac:TendererQualificationRequest has to exist with /cac:SpecificTendererRequirement/cbc:TendererRequirementTypeCode listname 'missing-info-submission'</assert>
      <!-- strictly speaking it is a cm rule, cause it is only required if BT-771 exists -->
      <assert id="CR-DE-BT-772-Lot" test="
          if ($SUBTYPE = $SUBTYPES-BT-771-772) then
            (count(cac:TendererQualificationRequest[not(cbc:CompanyLegalFormCode)]/cac:SpecificTendererRequirement[(cbc:TendererRequirementTypeCode[@listName = 'missing-info-submission'])]/cbc:Description) = 1)
          
          else
            true()" role="error">[CR-DE-BT-772-Lot] cbc:Description for BT-771 must exist in <name />.</assert>

      <!-- I think it is superflous now: sanity checks to make sure parent path for CR-DE-BT-771-Lot and CR-DE-BT-772-Lot exists -->
      <assert id="SR-DE-21" test="
          if ($SUBTYPE = $SUBTYPES-BT-771-772) then
            (count(cac:TendererQualificationRequest) >= 1)
          else
            true()" role="error">[SR-DE-21] Every <name /> has to have at least one cac:TendererQualificationRequest</assert>

      <assert id="CR-DE-BT-97-Lot" test="
          if ($SUBTYPE = $SUBTYPES-BT-97) then
            exists(cac:Language/cbc:ID)
          else
            true()" role="error">[CR-DE-BT-97-Lot] /cac:Language/cbc:ID must exist in <name /> for subtypes <value-of select="$SUBTYPES-BT-97" />.</assert>

      <!-- sanityrule for bt-15 lot-->
      <assert id="SR-DE-23" test="
          if ($SUBTYPE = $SUBTYPES-BT-15) then
            (count(cac:CallForTendersDocumentReference) >= 1)
          else
            true()" role="error">[SR-DE-23] cac:CallForTendersDocumentReference must exist at least once in <name />. </assert>

    </rule>

    <rule
      context="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Part']/cac:TenderingTerms">
      <!-- sanityrule for bt-15 part -->
      <assert id="SR-DE-24" test="
          if ($SUBTYPE = $SUBTYPES-BT-15) then
            (count(cac:CallForTendersDocumentReference) >= 1)
          else
            true()" role="error">[SR-DE-24] cac:CallForTendersDocumentReference must exist at least once in <name />. </assert>

    </rule>


    <!-- following  rules CR-DE-BT-708-Part and CR-DE-BT-708-Lot may be combined into single one -->
    <rule
      context="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']/cac:TenderingTerms/cac:CallForTendersDocumentReference">
      <assert id="CR-DE-BT-708-Lot" test="
          if ($SUBTYPE = $SUBTYPES-BT-708) then
            (count(cbc:LanguageID) ge 1)
          else
            true()" role="error">[CR-DE-BT-708-Lot] cbc:LanguageID must exist.</assert>

      <!-- bt-15 now only mandatory for non-restricted-document, BT-615 is restricted-document,but same xml field, and not mandatory-->
      <assert id="CR-DE-BT-15-Lot" test="
          if ($SUBTYPE = $SUBTYPES-BT-15) then
            if (cbc:DocumentType/text() = 'non-restricted-document') then
              (count(cac:Attachment/cac:ExternalReference/cbc:URI) ge 1)
            else
              true()
          else
            true()" role="error">[CR-DE-BT-15-Lot] /cac:Attachment/cac:ExternalReference/cbc:URI must exist in cac:CallForTendersDocumentReference for DocumentType non-restricted-document.</assert>
    </rule>

    <rule
      context="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Part']/cac:TenderingTerms/cac:CallForTendersDocumentReference">
      <assert id="CR-DE-BT-708-Part" test="
          if ($SUBTYPE = $SUBTYPES-BT-708) then
            (count(cbc:LanguageID) ge 1)
          else
            true()" role="error">[CR-DE-BT-708-Part] cbc:LanguageID must exist.</assert>

      <!--  bt-15 now only mandatory for non-restricted-document, BT-615 is restricted-document,but same xml field, and not mandatory-->
      <assert id="CR-DE-BT-15-Part" test="
          if ($SUBTYPE = $SUBTYPES-BT-15) then
            if (cbc:DocumentType/text() = 'non-restricted-document') then
              (count(cac:Attachment/cac:ExternalReference/cbc:URI) ge 1)
            else
              true()
          else
            true()" role="error">[CR-DE-BT-15-Part] /cac:Attachment/cac:ExternalReference/cbc:URI must exist in cac:CallForTendersDocumentReference for DocumentType non-restricted-document.</assert>

    </rule>




    <rule
      context="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Part']">

      <assert id="CR-DE-BT-726-Part" test="
          if ($SUBTYPE-CODE-NODE = $SUBTYPES-BT-726) then
            (boolean(normalize-space(cac:ProcurementProject/cbc:SMESuitableIndicator)))
          else
            true()" role="error">[CR-DE-BT-726-Part] cbc:SMESuitableIndicator must exist in <name />.</assert>

    </rule>


    <rule
      context="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'LotsGroup']">

      <assert id="CR-DE-BT-21-LotsGroup" test="boolean(normalize-space(cac:ProcurementProject/cbc:Name))" role="error">[CR-DE-BT-21-LotsGroup] /cac:ProcurementProject/cbc:Name must exist in <name />.</assert>

      <!-- commented-out because EU rules contradict the CELEX. In clarification.
      <assert id="CR-DE-BT-24-LotsGroup"
        test="boolean(normalize-space(cac:ProcurementProject/cbc:Description))"
        role="error">[CR-DE-BT-24-LotsGroup] cac:ProcurementProject/cbc:Description must exists.</assert>
        -->

      <assert id="CR-DE-BT-726-LotsGroup" test="
          if ($SUBTYPE-CODE-NODE = $SUBTYPES-BT-726) then
            (boolean(normalize-space(cac:ProcurementProject/cbc:SMESuitableIndicator)))
          else
            true()" role="error">[CR-DE-BT-726-LotsGroup] cbc:SMESuitableIndicator must exist in <name />.</assert>

    </rule>


    <rule
      context="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']">

      <assert id="CR-DE-BT-63-Lot" test="
          if ($SUBTYPE = $SUBTYPES-BT-63) then
            boolean(normalize-space(cac:TenderingTerms/cbc:VariantConstraintCode))
          else
            true()" role="error">[CR-DE-BT-63-Lot](<value-of select="$SUBTYPE" />) /cac:TenderingTerms/cbc:VariantConstraintCode must exist in <name /> in subtypes <value-of select="$SUBTYPES-BT-63" /></assert>

      <assert id="CR-DE-BT-17-Lot" test="
          if ($SUBTYPE = $SUBTYPES-BT-17) then
            boolean(normalize-space(cac:TenderingProcess/cbc:SubmissionMethodCode[@listName = 'esubmission']))
          else
            true()" role="error">[CR-DE-BT-17-Lot](<value-of select="$SUBTYPE" />) /cac:TenderingProcess/cbc:SubmissionMethodCode must exist in <name /> in subtypes <value-of select="$SUBTYPES-BT-17" />.</assert>


      <assert id="CR-DE-BT-717-Lot" test="
          if ($SUBTYPE = $SUBTYPES-BT-717) then 
            exists(cac:TenderingTerms/ext:UBLExtensions/ext:UBLExtension/ext:ExtensionContent/efext:EformsExtension/efac:StrategicProcurement/efbc:ApplicableLegalBasis[@listName='cvd-scope'] )
          else true()
      ">[CR-DE-BT-717-Lot] (<value-of select="$SUBTYPE" />) efbc:ApplicableLegalBasis must exist in subtypes <value-of select="$SUBTYPES-BT-717"/></assert>

      <assert id="CR-DE-BT-769-Lot" test="
          if ($SUBTYPE = $SUBTYPES-BT-769) then
            boolean(normalize-space(cac:TenderingTerms/cbc:MultipleTendersCode))
          else
            true()" role="error">[CR-DE-BT-769-Lot] (<value-of select="$SUBTYPE" />) /cac:TenderingTerms/cbc:MultipleTendrersCode must exist in <name /> in subtypes <value-of select="$SUBTYPES-BT-769" /></assert>

      <assert id="CR-DE-BT-726-Lot" test="
          if ($SUBTYPE-CODE-NODE = $SUBTYPES-BT-726) then
            (boolean(normalize-space(cac:ProcurementProject/cbc:SMESuitableIndicator)))
          else
            true()" role="error">[CR-DE-BT-726-Lot] cbc:SMESuitableIndicator must exist in <name />.</assert>

    </rule>



    <!-- seems to be redundant to EU SDK e.g rule|text|BR-BT-00191-0036">BT-191-Tender is forbidden for a notice with subtype 29</entry> -->
    <rule context="$NOTICE_RESULT/efac:LotTender/efac:Origin/efbc:AreaCode">
      <assert id="CR-DE-BT-191" test="false()" role="error">[CR-DE-BT-191] efbc:AreaCode is forbidden .</assert>
    </rule>

    <rule
      context="$ROOT-NODE/cac:TenderingTerms/cac:LotDistribution/cac:LotsGroup/cbc:LotsGroupID">
      <assert id="CR-DE-BT-330-Procedure" test="false()" role="error">[CR-DE-BT-330-Procedure] cbc:LotsGroupID is forbidden .</assert>
    </rule>


    <rule
      context="$ROOT-NODE/cac:TenderingTerms/cac:LotDistribution/cac:LotsGroup/cac:ProcurementProjectLotReference/cbc:ID[@schemeName = 'Lot']">
      <assert id="CR-DE-BT-1375-Procedure" test="false()" role="error">[CR-DE-BT-1375-Procedure] cbc:ID is forbidden .</assert>
    </rule>


    <rule
      context="$EXTENSION-NODE/efac:NoticeResult/efac:GroupFramework/efac:TenderLot/cbc:ID">
      <assert id="CR-DE-BT-556-NoticeResult" test="false()" role="error">[CR-DE-BT-556-NoticeResult] cbc:ID is forbidden.</assert>
    </rule>

  </pattern>

</schema>
