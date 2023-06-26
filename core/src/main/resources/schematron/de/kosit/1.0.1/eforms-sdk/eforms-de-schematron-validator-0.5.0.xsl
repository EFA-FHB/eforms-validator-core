<?xml version="1.0" encoding="UTF-8"?>
<xsl:transform xmlns:brin="http://data.europa.eu/p27/eforms-business-registration-information-notice/1"
                xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2"
                xmlns:can="urn:oasis:names:specification:ubl:schema:xsd:ContractAwardNotice-2"
                xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2"
                xmlns:cn="urn:oasis:names:specification:ubl:schema:xsd:ContractNotice-2"
                xmlns:efac="http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1"
                xmlns:efbc="http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1"
                xmlns:efext="http://data.europa.eu/p27/eforms-ubl-extensions/1"
                xmlns:error="https://doi.org/10.5281/zenodo.1495494#error"
                xmlns:ext="urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2"
                xmlns:pin="urn:oasis:names:specification:ubl:schema:xsd:PriorInformationNotice-2"
                xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
                xmlns:sch="http://purl.oclc.org/dsdl/schematron"
                xmlns:schxslt="https://doi.org/10.5281/zenodo.1495494"
                xmlns:schxslt-api="https://doi.org/10.5281/zenodo.1495494#api"
                xmlns:xs="http://www.w3.org/2001/XMLSchema"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="2.0">
   <rdf:Description xmlns:dc="http://purl.org/dc/elements/1.1/"
                     xmlns:dct="http://purl.org/dc/terms/"
                     xmlns:skos="http://www.w3.org/2004/02/skos/core#">
      <dct:creator>
         <dct:Agent>
            <skos:prefLabel>SchXslt/1.9.5 SAXON/HE 11.5</skos:prefLabel>
            <schxslt.compile.typed-variables xmlns="https://doi.org/10.5281/zenodo.1495494#">true</schxslt.compile.typed-variables>
         </dct:Agent>
      </dct:creator>
      <dct:created>2023-05-17T08:21:48.2566904+02:00</dct:created>
   </rdf:Description>
   <xsl:output indent="yes"/>
   <xsl:param name="EFORMS-DE-MAJOR-MINOR-VERSION" select="'1.0'"/>
   <xsl:param name="EFORMS-DE-ID"
               select="concat('eforms-de-', $EFORMS-DE-MAJOR-MINOR-VERSION)"/>
   <xsl:param name="SUBTYPES-ALL"
               select="('1', '2', '3', '4', '5', '6', 'E2', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', 'E3', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30', '31', '32', 'E4', '33', '34', '35', '36', '37', '38', '39', '40', 'E5')"/>
   <xsl:param name="SUBTYPES-BT-06"
               select="('7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', 'E3', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30', '31', '32', 'E4', '33', '34', '35', '36', '37', '38', '39', '40', 'E5')"/>
   <xsl:param name="SUBTYPES-BT-760"
               select="('29', '30', '31', '32', 'E4', '33', '34', '35', '36', '37', 'E5')"/>
   <xsl:param name="SUBTYPES-BT-15"
               select="('10', '11', '12', '13', '14', '15', '16', '17', '18', '19', 'E3', '20', '21', '22', '23', '24')"/>
   <xsl:param name="SUBTYPES-BT-708"
               select="('E1', '1', '2', '3', '4', '5', '6', 'E2', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', 'E3', '20', '21', '22', '23', '24')"/>
   <xsl:param name="SUBTYPES-BT-97-63-17"
               select="('7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', 'E3', '20', '21', '22', '23', '24')"/>
   <xsl:param name="SUBTYPES-BT-769"
               select="('E1', '7', '8', '9', '10', '11', '12', '13', '14', '16', '17', '18', '19', 'E3', '20', '21', '22', '23', '24')"/>
   <xsl:param name="SUBTYPES-BT-771-772"
               select="('7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', 'E3', '20', '21', '23', '24')"/>
   <xsl:param name="SUBTYPES-BT-726"
               select="('4', '5', '6', 'E2', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', 'E3', '20', '21', '22')"/>
   <xsl:param name="SUBTYPES-BT-17"
               select="('E1', '10', '11', '15', '16', '17', '23', '24')"/>
   <xsl:param name="SUBTYPES-BT-97"
               select="('E1', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', 'E3', '20', '21', '22', '23', '24')"/>
   <xsl:param name="SUBTYPES-BT-63"
               select="('7', '8', '9', '10', '11', '12', '13', '14', '16', '17', '18', '19', 'E3', '20', '21', '22', '23', '24')"/>
   <xsl:param name="ROOT-NODE"
               select="(/cn:ContractNotice | /pin:PriorInformationNotice | /can:ContractAwardNotice | /brin:BusinessRegistrationInformationNotice)"/>
   <xsl:param name="EXTENSION-NODE"
               select="$ROOT-NODE/ext:UBLExtensions/ext:UBLExtension/ext:ExtensionContent/efext:EformsExtension"/>
   <xsl:param name="NOTICE_RESULT" select="$EXTENSION-NODE/efac:NoticeResult"/>
   <xsl:param name="SUBTYPE-CODE-NODE"
               select="$EXTENSION-NODE/efac:NoticeSubType/cbc:SubTypeCode"/>
   <xsl:param name="SUBTYPE"
               select="normalize-space($EXTENSION-NODE/efac:NoticeSubType/cbc:SubTypeCode/text())"/>
   <xsl:param name="EXTENSION-ORG-NODE"
               select="$ROOT-NODE/ext:UBLExtensions/ext:UBLExtension/ext:ExtensionContent/efext:EformsExtension/efac:Organizations/efac:Organization"/>
   <xsl:template match="root()">
      <xsl:variable name="metadata" as="element()?">
         <svrl:metadata xmlns:dct="http://purl.org/dc/terms/"
                         xmlns:skos="http://www.w3.org/2004/02/skos/core#"
                         xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
            <dct:creator>
               <dct:Agent>
                  <skos:prefLabel>
                     <xsl:value-of separator="/"
                                    select="(system-property('xsl:product-name'), system-property('xsl:product-version'))"/>
                  </skos:prefLabel>
               </dct:Agent>
            </dct:creator>
            <dct:created>
               <xsl:value-of select="current-dateTime()"/>
            </dct:created>
            <dct:source>
               <rdf:Description xmlns:dc="http://purl.org/dc/elements/1.1/">
                  <dct:creator>
                     <dct:Agent>
                        <skos:prefLabel>SchXslt/1.9.5 SAXON/HE 11.5</skos:prefLabel>
                        <schxslt.compile.typed-variables xmlns="https://doi.org/10.5281/zenodo.1495494#">true</schxslt.compile.typed-variables>
                     </dct:Agent>
                  </dct:creator>
                  <dct:created>2023-05-17T08:21:48.2566904+02:00</dct:created>
               </rdf:Description>
            </dct:source>
         </svrl:metadata>
      </xsl:variable>
      <xsl:variable name="report" as="element(schxslt:report)">
         <schxslt:report>
            <xsl:call-template name="d15e54"/>
         </schxslt:report>
      </xsl:variable>
      <xsl:variable name="schxslt:report" as="node()*">
         <xsl:sequence select="$metadata"/>
         <xsl:for-each select="$report/schxslt:document">
            <xsl:for-each select="schxslt:pattern">
               <xsl:sequence select="node()"/>
               <xsl:sequence select="../schxslt:rule[@pattern = current()/@id]/node()"/>
            </xsl:for-each>
         </xsl:for-each>
      </xsl:variable>
      <svrl:schematron-output xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                               phase="eforms-de-phase"
                               title="eForms-DE Schematron Version @eforms-de-schematron.version.full@ compliant with eForms-DE specification @eforms-de.version.full@">
         <svrl:ns-prefix-in-attribute-values prefix="can"
                                              uri="urn:oasis:names:specification:ubl:schema:xsd:ContractAwardNotice-2"/>
         <svrl:ns-prefix-in-attribute-values prefix="cn"
                                              uri="urn:oasis:names:specification:ubl:schema:xsd:ContractNotice-2"/>
         <svrl:ns-prefix-in-attribute-values prefix="pin"
                                              uri="urn:oasis:names:specification:ubl:schema:xsd:PriorInformationNotice-2"/>
         <svrl:ns-prefix-in-attribute-values prefix="brin"
                                              uri="http://data.europa.eu/p27/eforms-business-registration-information-notice/1"/>
         <svrl:ns-prefix-in-attribute-values prefix="cbc"
                                              uri="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2"/>
         <svrl:ns-prefix-in-attribute-values prefix="cac"
                                              uri="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2"/>
         <svrl:ns-prefix-in-attribute-values prefix="ext"
                                              uri="urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2"/>
         <svrl:ns-prefix-in-attribute-values prefix="efac"
                                              uri="http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1"/>
         <svrl:ns-prefix-in-attribute-values prefix="efext" uri="http://data.europa.eu/p27/eforms-ubl-extensions/1"/>
         <svrl:ns-prefix-in-attribute-values prefix="efbc"
                                              uri="http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1"/>
         <svrl:ns-prefix-in-attribute-values prefix="xs" uri="http://www.w3.org/2001/XMLSchema"/>
         <xsl:sequence select="$schxslt:report"/>
      </svrl:schematron-output>
   </xsl:template>
   <xsl:template match="text() | @*" mode="#all" priority="-10"/>
   <xsl:template match="/" mode="#all" priority="-10">
      <xsl:apply-templates mode="#current" select="node()"/>
   </xsl:template>
   <xsl:template match="*" mode="#all" priority="-10">
      <xsl:apply-templates mode="#current" select="@*"/>
      <xsl:apply-templates mode="#current" select="node()"/>
   </xsl:template>
   <xsl:template name="d15e54">
      <schxslt:document>
         <schxslt:pattern id="d15e54">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                     name="codelists"
                                     id="codelists">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d15e209">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                     name="technical-sanity-pattern"
                                     id="technical-sanity-pattern">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d15e244">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                     name="cardinality-pattern"
                                     id="cardinality-pattern">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <xsl:apply-templates mode="d15e54" select="root()"/>
      </schxslt:document>
   </xsl:template>
   <xsl:template match="$ROOT-NODE/cac:ContractingParty/cac:ContractingPartyType/cbc:PartyTypeCode[@listName = 'buyer-contracting-type']"
                  priority="39"
                  mode="d15e54">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd15e54']">
            <schxslt:rule pattern="d15e54">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "$ROOT-NODE/cac:ContractingParty/cac:ContractingPartyType/cbc:PartyTypeCode[@listName = 'buyer-contracting-type']" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:ContractingParty/cac:ContractingPartyType/cbc:PartyTypeCode[@listName = 'buyer-contracting-type']</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d15e54">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:ContractingParty/cac:ContractingPartyType/cbc:PartyTypeCode[@listName = 'buyer-contracting-type']</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(. = ('cont-ent','not-cont-ent'))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CL-DE-BT-740">
                     <xsl:attribute name="test">. = ('cont-ent','not-cont-ent')</xsl:attribute>
                     <svrl:text>[CL-DE-BT-740] Value must be one from codelist urn:xeinkauf:eforms-de:codelist:buyer-contracting-type.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="($schxslt:patterns-matched, 'd15e54')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="$ROOT-NODE/cac:ContractingParty/cac:ContractingPartyType/cbc:PartyTypeCode[@listName = 'buyer-legal-type']"
                  priority="38"
                  mode="d15e54">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd15e54']">
            <schxslt:rule pattern="d15e54">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "$ROOT-NODE/cac:ContractingParty/cac:ContractingPartyType/cbc:PartyTypeCode[@listName = 'buyer-legal-type']" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:ContractingParty/cac:ContractingPartyType/cbc:PartyTypeCode[@listName = 'buyer-legal-type']</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d15e54">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:ContractingParty/cac:ContractingPartyType/cbc:PartyTypeCode[@listName = 'buyer-legal-type']</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(. = ('koerp-oer-bund','anst-oer-bund','stift-oer-bund','koerp-oer-kommun','anst-oer-kommun','stift-oer-kommun','koerp-oer-land','anst-oer-land','stift-oer-land','oberst-bbeh','omu-bbeh-niedrig','omu-bbeh','def-cont','eu-ins-bod-ag','grp-p-aut','int-org','kbeh','org-sub','pub-undert','pub-undert-cga','pub-undert-la','pub-undert-ra','oberst-lbeh','omu-lbeh','spec-rights-entity'))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CL-DE-BT-11">
                     <xsl:attribute name="test">. = ('koerp-oer-bund','anst-oer-bund','stift-oer-bund','koerp-oer-kommun','anst-oer-kommun','stift-oer-kommun','koerp-oer-land','anst-oer-land','stift-oer-land','oberst-bbeh','omu-bbeh-niedrig','omu-bbeh','def-cont','eu-ins-bod-ag','grp-p-aut','int-org','kbeh','org-sub','pub-undert','pub-undert-cga','pub-undert-la','pub-undert-ra','oberst-lbeh','omu-lbeh','spec-rights-entity')</xsl:attribute>
                     <svrl:text>[CL-DE-BT-11] Value must be one from codelist urn:xeinkauf:eforms-de:codelist:eforms-buyer-legal-type.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="($schxslt:patterns-matched, 'd15e54')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="$ROOT-NODE/cac:TenderingTerms/cac:TendererQualificationRequest/cac:SpecificTendererRequirement/cbc:TendererRequirementTypeCode[@listName = 'exclusion-ground']"
                  priority="37"
                  mode="d15e54">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd15e54']">
            <schxslt:rule pattern="d15e54">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "$ROOT-NODE/cac:TenderingTerms/cac:TendererQualificationRequest/cac:SpecificTendererRequirement/cbc:TendererRequirementTypeCode[@listName = 'exclusion-ground']" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:TenderingTerms/cac:TendererQualificationRequest/cac:SpecificTendererRequirement/cbc:TendererRequirementTypeCode[@listName = 'exclusion-ground']</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d15e54">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:TenderingTerms/cac:TendererQualificationRequest/cac:SpecificTendererRequirement/cbc:TendererRequirementTypeCode[@listName = 'exclusion-ground']</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(. = ('bankr-nat','bankruptcy','corruption','cred-arran','crime-org','distorsion','envir-law','finan-laund','fraud','human-traffic','insolvency','labour-law','liq-admin','misinterpr','nati-ground','ex-os','ex-us','partic-confl','prep-confl','prof-misconduct','sanction','socsec-law','socsec-pay','susp-act','tax-pay','terr-offence'))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CL-DE-BT-67">
                     <xsl:attribute name="test">. = ('bankr-nat','bankruptcy','corruption','cred-arran','crime-org','distorsion','envir-law','finan-laund','fraud','human-traffic','insolvency','labour-law','liq-admin','misinterpr','nati-ground','ex-os','ex-us','partic-confl','prep-confl','prof-misconduct','sanction','socsec-law','socsec-pay','susp-act','tax-pay','terr-offence')</xsl:attribute>
                     <svrl:text>[CL-DE-BT-67] Value must be one from codelist urn:xeinkauf:eforms-de:codelist:exclusion-ground.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="($schxslt:patterns-matched, 'd15e54')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="$EXTENSION-ORG-NODE/efac:Company[(cac:PartyIdentification/cbc:ID/text() = //efac:TenderingParty/efac:Tenderer/cbc:ID/text()) or (cac:PartyIdentification/cbc:ID/text() = //efac:TenderingParty/efac:Subcontractor/cbc:ID/text())]/efbc:CompanySizeCode"
                  priority="36"
                  mode="d15e54">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd15e54']">
            <schxslt:rule pattern="d15e54">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "$EXTENSION-ORG-NODE/efac:Company[(cac:PartyIdentification/cbc:ID/text() = //efac:TenderingParty/efac:Tenderer/cbc:ID/text()) or (cac:PartyIdentification/cbc:ID/text() = //efac:TenderingParty/efac:Subcontractor/cbc:ID/text())]/efbc:CompanySizeCode" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$EXTENSION-ORG-NODE/efac:Company[(cac:PartyIdentification/cbc:ID/text() = //efac:TenderingParty/efac:Tenderer/cbc:ID/text()) or (cac:PartyIdentification/cbc:ID/text() = //efac:TenderingParty/efac:Subcontractor/cbc:ID/text())]/efbc:CompanySizeCode</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d15e54">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$EXTENSION-ORG-NODE/efac:Company[(cac:PartyIdentification/cbc:ID/text() = //efac:TenderingParty/efac:Tenderer/cbc:ID/text()) or (cac:PartyIdentification/cbc:ID/text() = //efac:TenderingParty/efac:Subcontractor/cbc:ID/text())]/efbc:CompanySizeCode</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(. = ('large','medium','micro','small'))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CL-DE-BT-165">
                     <xsl:attribute name="test">. = ('large','medium','micro','small')</xsl:attribute>
                     <svrl:text>[CL-DE-BT-165] Value must be one from codelist urn:xeinkauf:eforms-de:codelist:economic-operator-size:v1.0.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="($schxslt:patterns-matched, 'd15e54')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']/cac:TenderingTerms/cac:TendererQualificationRequest[not(cbc:CompanyLegalFormCode)]/cac:SpecificTendererRequirement[not(cbc:TendererRequirementTypeCode[@listName = 'reserved-procurement'])]/cbc:TendererRequirementTypeCode[@listName = 'missing-info-submission']"
                  priority="35"
                  mode="d15e54">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd15e54']">
            <schxslt:rule pattern="d15e54">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']/cac:TenderingTerms/cac:TendererQualificationRequest[not(cbc:CompanyLegalFormCode)]/cac:SpecificTendererRequirement[not(cbc:TendererRequirementTypeCode[@listName = 'reserved-procurement'])]/cbc:TendererRequirementTypeCode[@listName = 'missing-info-submission']" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']/cac:TenderingTerms/cac:TendererQualificationRequest[not(cbc:CompanyLegalFormCode)]/cac:SpecificTendererRequirement[not(cbc:TendererRequirementTypeCode[@listName = 'reserved-procurement'])]/cbc:TendererRequirementTypeCode[@listName = 'missing-info-submission']</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d15e54">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']/cac:TenderingTerms/cac:TendererQualificationRequest[not(cbc:CompanyLegalFormCode)]/cac:SpecificTendererRequirement[not(cbc:TendererRequirementTypeCode[@listName = 'reserved-procurement'])]/cbc:TendererRequirementTypeCode[@listName = 'missing-info-submission']</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(. = ('late-all','late-some','late-none'))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CL-DE-BT-771">
                     <xsl:attribute name="test">. = ('late-all','late-some','late-none')</xsl:attribute>
                     <svrl:text>[CL-DE-BT-771] Value must be one from codelist urn:xeinkauf:eforms-de:codelist:missing-info-submission.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="($schxslt:patterns-matched, 'd15e54')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="$ROOT-NODE/cac:TenderingProcess/cbc:ProcedureCode"
                  priority="34"
                  mode="d15e54">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd15e54']">
            <schxslt:rule pattern="d15e54">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "$ROOT-NODE/cac:TenderingProcess/cbc:ProcedureCode" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:TenderingProcess/cbc:ProcedureCode</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d15e54">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:TenderingProcess/cbc:ProcedureCode</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(. = ('comp-dial','innovation','neg-w-call','us-neg-w-call','neg-wo-call','us-neg-wo-call','open','us-open','oth-mult','oth-single','us-free','us-free-tw','us-free-no-tw','restricted','us-res-tw','us-res-no-tw'))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CL-DE-BT-105">
                     <xsl:attribute name="test">. = ('comp-dial','innovation','neg-w-call','us-neg-w-call','neg-wo-call','us-neg-wo-call','open','us-open','oth-mult','oth-single','us-free','us-free-tw','us-free-no-tw','restricted','us-res-tw','us-res-no-tw')</xsl:attribute>
                     <svrl:text>[CL-DE-BT-105] Value must be one from codelist urn:xeinkauf:eforms-de:codelist:procurement-procedure-type.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="($schxslt:patterns-matched, 'd15e54')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']/cac:ProcurementProject/cac:ProcurementAdditionalType/cbc:ProcurementTypeCode[@listName = 'gpp-criteria']"
                  priority="33"
                  mode="d15e54">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd15e54']">
            <schxslt:rule pattern="d15e54">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']/cac:ProcurementProject/cac:ProcurementAdditionalType/cbc:ProcurementTypeCode[@listName = 'gpp-criteria']" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']/cac:ProcurementProject/cac:ProcurementAdditionalType/cbc:ProcurementTypeCode[@listName = 'gpp-criteria']</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d15e54">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']/cac:ProcurementProject/cac:ProcurementAdditionalType/cbc:ProcurementTypeCode[@listName = 'gpp-criteria']</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(. = ('emas-com','ene-ef-com','iso-14001-com','iso-14024-com','reg-834-2007-com','kosten-lebenszyklus','other'))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CL-DE-BT-805">
                     <xsl:attribute name="test">. = ('emas-com','ene-ef-com','iso-14001-com','iso-14024-com','reg-834-2007-com','kosten-lebenszyklus','other')</xsl:attribute>
                     <svrl:text>[CL-DE-BT-805] Value must be one from codelist urn:xeinkauf:eforms-de:codelist:gpp-criteria.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="($schxslt:patterns-matched, 'd15e54')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="$ROOT-NODE/cac:TenderingTerms/cac:ProcurementLegislationDocumentReference/cbc:ID"
                  priority="32"
                  mode="d15e54">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd15e54']">
            <schxslt:rule pattern="d15e54">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "$ROOT-NODE/cac:TenderingTerms/cac:ProcurementLegislationDocumentReference/cbc:ID" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:TenderingTerms/cac:ProcurementLegislationDocumentReference/cbc:ID</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d15e54">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:TenderingTerms/cac:ProcurementLegislationDocumentReference/cbc:ID</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(. = ('vsvgv','vob-a-vs','konzvgv','vgv','vob-a-eu','sektvo','other','sgb-vi','vob-a','uvgo','vol-a','sl-other','svhv'))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CL-DE-BT-01">
                     <xsl:attribute name="test">. = ('vsvgv','vob-a-vs','konzvgv','vgv','vob-a-eu','sektvo','other','sgb-vi','vob-a','uvgo','vol-a','sl-other','svhv')</xsl:attribute>
                     <svrl:text>[CL-DE-BT-01] Value must be one from codelist urn:xeinkauf:eforms-de:codelist:eforms-legal-basis.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="($schxslt:patterns-matched, 'd15e54')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="$EXTENSION-NODE/efac:NoticeResult/efac:LotResult/efac:ReceivedSubmissionsStatistics/efbc:StatisticsCode"
                  priority="31"
                  mode="d15e54">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd15e54']">
            <schxslt:rule pattern="d15e54">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "$EXTENSION-NODE/efac:NoticeResult/efac:LotResult/efac:ReceivedSubmissionsStatistics/efbc:StatisticsCode" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$EXTENSION-NODE/efac:NoticeResult/efac:LotResult/efac:ReceivedSubmissionsStatistics/efbc:StatisticsCode</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d15e54">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$EXTENSION-NODE/efac:NoticeResult/efac:LotResult/efac:ReceivedSubmissionsStatistics/efbc:StatisticsCode</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(. = ('part-req','t-esubm','t-med','t-micro','t-no-eea','t-no-verif','t-oth-eea','t-small','t-sme','t-verif-inad','t-verif-inad-low','tenders'))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CL-DE-BT-760">
                     <xsl:attribute name="test">. = ('part-req','t-esubm','t-med','t-micro','t-no-eea','t-no-verif','t-oth-eea','t-small','t-sme','t-verif-inad','t-verif-inad-low','tenders')</xsl:attribute>
                     <svrl:text>[CL-DE-BT-760] Value must be one from codelist urn:xeinkauf:eforms-de:codelist:received-submission-type:v1.0.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="($schxslt:patterns-matched, 'd15e54')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']/cac:ProcurementProject/cac:ProcurementAdditionalType/cbc:ProcurementTypeCode[@listName = 'social-objective']"
                  priority="30"
                  mode="d15e54">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd15e54']">
            <schxslt:rule pattern="d15e54">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']/cac:ProcurementProject/cac:ProcurementAdditionalType/cbc:ProcurementTypeCode[@listName = 'social-objective']" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']/cac:ProcurementProject/cac:ProcurementAdditionalType/cbc:ProcurementTypeCode[@listName = 'social-objective']</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d15e54">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']/cac:ProcurementProject/cac:ProcurementAdditionalType/cbc:ProcurementTypeCode[@listName = 'social-objective']</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(. = ('acc-all','et-eq','gen-eq','hum-right','opp','other','iao-core','work-cond'))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CL-DE-BT-775">
                     <xsl:attribute name="test">. = ('acc-all','et-eq','gen-eq','hum-right','opp','other','iao-core','work-cond')</xsl:attribute>
                     <svrl:text>[CL-DE-BT-775] Value must be one from codelist urn:xeinkauf:eforms-de:codelist:social-objective.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="($schxslt:patterns-matched, 'd15e54')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="$ROOT-NODE/cbc:CustomizationID" priority="29" mode="d15e54">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd15e209']">
            <schxslt:rule pattern="d15e209">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "$ROOT-NODE/cbc:CustomizationID" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cbc:CustomizationID</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d15e209">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cbc:CustomizationID</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(text() = $EFORMS-DE-ID)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="SR-DE-1">
                     <xsl:attribute name="test">text() = $EFORMS-DE-ID</xsl:attribute>
                     <svrl:text>[SR-DE-1 ]The value <xsl:value-of select="."/> of <xsl:value-of select="name()"/> must be equal to the current version (<xsl:value-of select="$EFORMS-DE-ID"/>) of Standards eForms-DE. </svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="($schxslt:patterns-matched, 'd15e209')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="$EXTENSION-NODE" priority="28" mode="d15e54">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd15e209']">
            <schxslt:rule pattern="d15e209">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "$EXTENSION-NODE" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$EXTENSION-NODE</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d15e209">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$EXTENSION-NODE</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(efac:NoticeSubType/cbc:SubTypeCode)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="SR-DE-2">
                     <xsl:attribute name="test">efac:NoticeSubType/cbc:SubTypeCode</xsl:attribute>
                     <svrl:text>[SR-DE-2] The element efac:NoticeSubType/cbc:SubTypeCode must exist as child of <xsl:value-of select="name()"/>.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="($schxslt:patterns-matched, 'd15e209')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="$SUBTYPE-CODE-NODE" priority="27" mode="d15e54">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd15e209']">
            <schxslt:rule pattern="d15e209">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "$SUBTYPE-CODE-NODE" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$SUBTYPE-CODE-NODE</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d15e209">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$SUBTYPE-CODE-NODE</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(($SUBTYPE = $SUBTYPES-ALL))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="SR-DE-3">
                     <xsl:attribute name="test">($SUBTYPE = $SUBTYPES-ALL)</xsl:attribute>
                     <svrl:text>[SR-DE-3] SubTypeCode <xsl:value-of select="."/> is not valid. It must be a value from this list <xsl:value-of select="$SUBTYPES-ALL"/>.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="($schxslt:patterns-matched, 'd15e209')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="$ROOT-NODE" priority="26" mode="d15e54">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd15e244']">
            <schxslt:rule pattern="d15e244">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "$ROOT-NODE" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d15e244">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(boolean(normalize-space(cbc:RequestedPublicationDate)))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-738">
                     <xsl:attribute name="test">boolean(normalize-space(cbc:RequestedPublicationDate))</xsl:attribute>
                     <svrl:text>[CR-DE-BT-738] cbc:RequestedPublicationDate is mandatory.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(cac:TenderingTerms)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       id="SR-DE-01">
                     <xsl:attribute name="test">cac:TenderingTerms</xsl:attribute>
                     <svrl:text>[SR-DE-01] TenderingTerms must exist in all Notice Types</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(exists(cac:TenderingTerms/cac:ProcurementLegislationDocumentReference/cbc:ID))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-BT-01-Germany">
                     <xsl:attribute name="test">exists(cac:TenderingTerms/cac:ProcurementLegislationDocumentReference/cbc:ID)</xsl:attribute>
                     <svrl:text>[CR-BT-01-Germany] cac:ProcurementLegislationDocumentReference/cbc:ID must exist.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="($schxslt:patterns-matched, 'd15e244')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="$EXTENSION-NODE/efac:Organizations/efac:UltimateBeneficialOwner"
                  priority="25"
                  mode="d15e54">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd15e244']">
            <schxslt:rule pattern="d15e244">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "$EXTENSION-NODE/efac:Organizations/efac:UltimateBeneficialOwner" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$EXTENSION-NODE/efac:Organizations/efac:UltimateBeneficialOwner</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d15e244">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$EXTENSION-NODE/efac:Organizations/efac:UltimateBeneficialOwner</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(count(efac:Nationality/cbc:NationalityID) = 1)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-706-UBO">
                     <xsl:attribute name="test">count(efac:Nationality/cbc:NationalityID) = 1</xsl:attribute>
                     <svrl:text>[CR-DE-BT-706-UBO] An efac:UltimateBeneficialOwner has to have on efac:Nationality/cbc:NationalityID</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="($schxslt:patterns-matched, 'd15e244')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="$EXTENSION-ORG-NODE/efac:Company"
                  priority="24"
                  mode="d15e54">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:variable name="PARTY-LEGAL-ENTITY-NODE" select="cac:PartyLegalEntity"/>
      <xsl:variable name="ADDRESS-NODE" select="cac:PostalAddress"/>
      <xsl:variable name="CONTACT-NODE" select="cac:Contact"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd15e244']">
            <schxslt:rule pattern="d15e244">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "$EXTENSION-ORG-NODE/efac:Company" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$EXTENSION-ORG-NODE/efac:Company</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d15e244">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$EXTENSION-ORG-NODE/efac:Company</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(count($PARTY-LEGAL-ENTITY-NODE) = 1)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="SR-DE-10">
                     <xsl:attribute name="test">count($PARTY-LEGAL-ENTITY-NODE) = 1</xsl:attribute>
                     <svrl:text>[SR-DE-10] Every <xsl:value-of select="name()"/> has to have one cac:PartyLegalEntity</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(boolean(normalize-space(cac:Contact/cbc:ElectronicMail)))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-506">
                     <xsl:attribute name="test">boolean(normalize-space(cac:Contact/cbc:ElectronicMail))</xsl:attribute>
                     <svrl:text>[CR-DE-BT-506] Every Buyer (<xsl:value-of select="name()"/>) must have a cac:Contact/cbc:ElectronicMail.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(count($ADDRESS-NODE) = 1)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="SR-DE-4">
                     <xsl:attribute name="test">count($ADDRESS-NODE) = 1</xsl:attribute>
                     <svrl:text>[SR-DE-4] Every <xsl:value-of select="name()"/> has to have one cac:PostalAddress</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(count($ADDRESS-NODE/cac:Country) = 1)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="SR-DE-7">
                     <xsl:attribute name="test">count($ADDRESS-NODE/cac:Country) = 1</xsl:attribute>
                     <svrl:text>[SR-DE-7] Every <xsl:value-of select="name()"/> has to have one cac:Country</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(count($CONTACT-NODE) = 1)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="SR-DE-9">
                     <xsl:attribute name="test">count($CONTACT-NODE) = 1</xsl:attribute>
                     <svrl:text>[SR-DE-9] Every <xsl:value-of select="name()"/> has to have one cac:Contact</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(boolean(normalize-space(cac:PartyName/cbc:Name)))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-500">
                     <xsl:attribute name="test">boolean(normalize-space(cac:PartyName/cbc:Name))</xsl:attribute>
                     <svrl:text>[CR-DE-BT-5] Every <xsl:value-of select="name()"/> must have a Name.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(boolean(normalize-space($ADDRESS-NODE/cbc:CityName)))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-513">
                     <xsl:attribute name="test">boolean(normalize-space($ADDRESS-NODE/cbc:CityName))</xsl:attribute>
                     <svrl:text>[CR-DE-BT-513] Every <xsl:value-of select="name()"/> must have a cbc:CityName.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(boolean(normalize-space($ADDRESS-NODE/cbc:PostalZone)))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-512"
                                       see="cac:PostalAddress">
                     <xsl:attribute name="test">boolean(normalize-space($ADDRESS-NODE/cbc:PostalZone))</xsl:attribute>
                     <svrl:text>[CR-DE-BT-512] <xsl:value-of select="name()"/> Must have a PostalZone.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(boolean(normalize-space($ADDRESS-NODE/cbc:CountrySubentityCode)))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-507">
                     <xsl:attribute name="test">boolean(normalize-space($ADDRESS-NODE/cbc:CountrySubentityCode))</xsl:attribute>
                     <svrl:text>[CR-DE-BT-507] Every <xsl:value-of select="name()"/> must have CountrySubentityCode.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(boolean(normalize-space($ADDRESS-NODE/cac:Country/cbc:IdentificationCode)))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-514">
                     <xsl:attribute name="test">boolean(normalize-space($ADDRESS-NODE/cac:Country/cbc:IdentificationCode))</xsl:attribute>
                     <svrl:text>[CR-DE-BT-514] Every <xsl:value-of select="name()"/>must have a  cac:Country/cbc:IdentificationCode.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(count($CONTACT-NODE/cbc:Telefax) le 1)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-739">
                     <xsl:attribute name="test">count($CONTACT-NODE/cbc:Telefax) le 1</xsl:attribute>
                     <svrl:text>[CR-DE-BT-739]In every <xsl:value-of select="name()"/> cac:Contact/cbc:Telefax may only occure ones.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="($schxslt:patterns-matched, 'd15e244')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="$EXTENSION-ORG-NODE/efac:TouchPoint"
                  priority="23"
                  mode="d15e54">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:variable name="ADDRESS-NODE" select="cac:PostalAddress"/>
      <xsl:variable name="CONTACT-NODE" select="cac:Contact"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd15e244']">
            <schxslt:rule pattern="d15e244">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "$EXTENSION-ORG-NODE/efac:TouchPoint" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$EXTENSION-ORG-NODE/efac:TouchPoint</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d15e244">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$EXTENSION-ORG-NODE/efac:TouchPoint</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(count($ADDRESS-NODE) = 1)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="SR-DE-4">
                     <xsl:attribute name="test">count($ADDRESS-NODE) = 1</xsl:attribute>
                     <svrl:text>[SR-DE-4] Every <xsl:value-of select="name()"/> has to have one cac:PostalAddress</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(count($ADDRESS-NODE/cac:Country) = 1)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="SR-DE-7">
                     <xsl:attribute name="test">count($ADDRESS-NODE/cac:Country) = 1</xsl:attribute>
                     <svrl:text>[SR-DE-7] Every <xsl:value-of select="name()"/> has to have one cac:Country</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(count($CONTACT-NODE) = 1)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="SR-DE-9">
                     <xsl:attribute name="test">count($CONTACT-NODE) = 1</xsl:attribute>
                     <svrl:text>[SR-DE-9] Every <xsl:value-of select="name()"/> has to have one cac:Contact</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(boolean(normalize-space(cac:PartyName/cbc:Name)))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-500">
                     <xsl:attribute name="test">boolean(normalize-space(cac:PartyName/cbc:Name))</xsl:attribute>
                     <svrl:text>[CR-DE-BT-5] Every <xsl:value-of select="name()"/> must have a Name.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(boolean(normalize-space($ADDRESS-NODE/cbc:CityName)))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-513">
                     <xsl:attribute name="test">boolean(normalize-space($ADDRESS-NODE/cbc:CityName))</xsl:attribute>
                     <svrl:text>[CR-DE-BT-513] Every <xsl:value-of select="name()"/> must have a cbc:CityName.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(boolean(normalize-space($ADDRESS-NODE/cbc:PostalZone)))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-512"
                                       see="cac:PostalAddress">
                     <xsl:attribute name="test">boolean(normalize-space($ADDRESS-NODE/cbc:PostalZone))</xsl:attribute>
                     <svrl:text>[CR-DE-BT-512] <xsl:value-of select="name()"/> Must have a PostalZone.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(boolean(normalize-space($ADDRESS-NODE/cbc:CountrySubentityCode)))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-507">
                     <xsl:attribute name="test">boolean(normalize-space($ADDRESS-NODE/cbc:CountrySubentityCode))</xsl:attribute>
                     <svrl:text>[CR-DE-BT-507] Every <xsl:value-of select="name()"/> must have CountrySubentityCode.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(boolean(normalize-space($ADDRESS-NODE/cac:Country/cbc:IdentificationCode)))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-514">
                     <xsl:attribute name="test">boolean(normalize-space($ADDRESS-NODE/cac:Country/cbc:IdentificationCode))</xsl:attribute>
                     <svrl:text>[CR-DE-BT-514] Every <xsl:value-of select="name()"/>must have a  cac:Country/cbc:IdentificationCode.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(count($CONTACT-NODE/cbc:Telefax) le 1)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-739">
                     <xsl:attribute name="test">count($CONTACT-NODE/cbc:Telefax) le 1</xsl:attribute>
                     <svrl:text>[CR-DE-BT-739]In every <xsl:value-of select="name()"/> cac:Contact/cbc:Telefax may only occure ones.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="($schxslt:patterns-matched, 'd15e244')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="$EXTENSION-ORG-NODE/efac:Company/cac:PartyLegalEntity"
                  priority="22"
                  mode="d15e54">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd15e244']">
            <schxslt:rule pattern="d15e244">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "$EXTENSION-ORG-NODE/efac:Company/cac:PartyLegalEntity" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$EXTENSION-ORG-NODE/efac:Company/cac:PartyLegalEntity</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d15e244">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$EXTENSION-ORG-NODE/efac:Company/cac:PartyLegalEntity</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(boolean(normalize-space(cbc:CompanyID)))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-501"
                                       see="cac:PartyLegalEntity">
                     <xsl:attribute name="test">boolean(normalize-space(cbc:CompanyID))</xsl:attribute>
                     <svrl:text>[CR-DE-BT-501] Every <xsl:value-of select="name()"/> must have a cbc:CompanyID.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="($schxslt:patterns-matched, 'd15e244')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="($EXTENSION-ORG-NODE/efac:TouchPoint union $EXTENSION-ORG-NODE/efac:Company)/cbc:EndpointID"
                  priority="21"
                  mode="d15e54">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd15e244']">
            <schxslt:rule pattern="d15e244">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "($EXTENSION-ORG-NODE/efac:TouchPoint union $EXTENSION-ORG-NODE/efac:Company)/cbc:EndpointID" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">($EXTENSION-ORG-NODE/efac:TouchPoint union $EXTENSION-ORG-NODE/efac:Company)/cbc:EndpointID</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d15e244">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">($EXTENSION-ORG-NODE/efac:TouchPoint union $EXTENSION-ORG-NODE/efac:Company)/cbc:EndpointID</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(false())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-509"
                                       see="cbc:EndpointID">
                     <xsl:attribute name="test">false()</xsl:attribute>
                     <svrl:text>[CR-DE-BT-509] forbidden.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="($schxslt:patterns-matched, 'd15e244')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="$NOTICE_RESULT/efac:SettledContract"
                  priority="20"
                  mode="d15e54">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd15e244']">
            <schxslt:rule pattern="d15e244">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "$NOTICE_RESULT/efac:SettledContract" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$NOTICE_RESULT/efac:SettledContract</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d15e244">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$NOTICE_RESULT/efac:SettledContract</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(count(cbc:URI) = 0)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-151-Contract">
                     <xsl:attribute name="test">count(cbc:URI) = 0</xsl:attribute>
                     <svrl:text>[CR-DE-BT-151-Contract] cbc:URI forbidden in <xsl:value-of select="name()"/>
                     </svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="($schxslt:patterns-matched, 'd15e244')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="$EXTENSION-NODE/efac:NoticeResult/efac:LotResult/efac:ReceivedSubmissionsStatistics"
                  priority="19"
                  mode="d15e54">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd15e244']">
            <schxslt:rule pattern="d15e244">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "$EXTENSION-NODE/efac:NoticeResult/efac:LotResult/efac:ReceivedSubmissionsStatistics" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$EXTENSION-NODE/efac:NoticeResult/efac:LotResult/efac:ReceivedSubmissionsStatistics</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d15e244">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$EXTENSION-NODE/efac:NoticeResult/efac:LotResult/efac:ReceivedSubmissionsStatistics</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(           if ($SUBTYPE = $SUBTYPES-BT-760) then             boolean(normalize-space(efbc:StatisticsCode))           else             true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-760-LotResult">
                     <xsl:attribute name="test">           if ($SUBTYPE = $SUBTYPES-BT-760) then             boolean(normalize-space(efbc:StatisticsCode))           else             true()</xsl:attribute>
                     <svrl:text>efbc:StatisticsCode must exists.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="($schxslt:patterns-matched, 'd15e244')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="$ROOT-NODE/cac:ProcurementProject"
                  priority="18"
                  mode="d15e54">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd15e244']">
            <schxslt:rule pattern="d15e244">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "$ROOT-NODE/cac:ProcurementProject" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:ProcurementProject</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d15e244">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:ProcurementProject</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(count(cac:RealizedLocation/cac:Address) = 1)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="SR-DE-11">
                     <xsl:attribute name="test">count(cac:RealizedLocation/cac:Address) = 1</xsl:attribute>
                     <svrl:text>Every <xsl:value-of select="name()"/> has to have cac:Address</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(count(cac:RealizedLocation/cac:Address/cac:Country) = 1)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="SR-DE-14">
                     <xsl:attribute name="test">count(cac:RealizedLocation/cac:Address/cac:Country) = 1</xsl:attribute>
                     <svrl:text>Every <xsl:value-of select="name()"/> has to have cac:Country</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(boolean(normalize-space(cbc:ProcurementTypeCode)))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-23">
                     <xsl:attribute name="test">boolean(normalize-space(cbc:ProcurementTypeCode))</xsl:attribute>
                     <svrl:text>[CR-DE-BT-23] cbc:ProcurementTypeCode must exist as child of <xsl:value-of select="name()"/>.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(boolean(normalize-space(cbc:Name)))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-21">
                     <xsl:attribute name="test">boolean(normalize-space(cbc:Name))</xsl:attribute>
                     <svrl:text>[CR-DE-BT-21] cbc:Name must exist as child of <xsl:value-of select="name()"/>.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(boolean(normalize-space(cac:RealizedLocation/cac:Address/cbc:CountrySubentityCode)))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-5071-Procedure">
                     <xsl:attribute name="test">boolean(normalize-space(cac:RealizedLocation/cac:Address/cbc:CountrySubentityCode))</xsl:attribute>
                     <svrl:text>[CR-DE-BT-5071-Procedure] cac:RealizedLocation/cac:Address/cbc:CountrySubentityCode must exist as child of <xsl:value-of select="name()"/>.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(boolean(normalize-space(cac:RealizedLocation/cac:Address/cac:Country/cbc:IdentificationCode)))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-5141-Procedure">
                     <xsl:attribute name="test">boolean(normalize-space(cac:RealizedLocation/cac:Address/cac:Country/cbc:IdentificationCode))</xsl:attribute>
                     <svrl:text>[CR-DE-BT-5141-Procedure] cac:RealizedLocation/cac:Address/cac:Country/cbc:IdentificationCode must exist as child of <xsl:value-of select="name()"/>.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="($schxslt:patterns-matched, 'd15e244')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']/cac:ProcurementProject"
                  priority="17"
                  mode="d15e54">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd15e244']">
            <schxslt:rule pattern="d15e244">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']/cac:ProcurementProject" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']/cac:ProcurementProject</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d15e244">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']/cac:ProcurementProject</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(boolean(normalize-space(cbc:ProcurementTypeCode[@listName = 'contract-nature'])))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-23-Lot">
                     <xsl:attribute name="test">boolean(normalize-space(cbc:ProcurementTypeCode[@listName = 'contract-nature']))</xsl:attribute>
                     <svrl:text>[CR-DE-BT-23-Lot] cbc:ProcurementTypeCode must exist as child of <xsl:value-of select="name()"/>.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(boolean(normalize-space(cbc:Name)))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-21-Lot">
                     <xsl:attribute name="test">boolean(normalize-space(cbc:Name))</xsl:attribute>
                     <svrl:text>[CR-DE-BT-21-Lot] cbc:Name must exist as child of <xsl:value-of select="name()"/>.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(           if ($SUBTYPE = $SUBTYPES-BT-06) then             exists(cac:ProcurementAdditionalType)           else             true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-06-Lot-1">
                     <xsl:attribute name="test">           if ($SUBTYPE = $SUBTYPES-BT-06) then             exists(cac:ProcurementAdditionalType)           else             true()</xsl:attribute>
                     <svrl:text>[CR-DE-BT-06-Lot-1] The 'cac:ProcurementAdditionalType' must exist.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(           if ($SUBTYPE = $SUBTYPES-BT-06) then             (count(cac:ProcurementAdditionalType/cbc:ProcurementTypeCode[@listName = 'strategic-procurement']) ge 1)           else             true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-06-Lot">
                     <xsl:attribute name="test">           if ($SUBTYPE = $SUBTYPES-BT-06) then             (count(cac:ProcurementAdditionalType/cbc:ProcurementTypeCode[@listName = 'strategic-procurement']) ge 1)           else             true()</xsl:attribute>
                     <svrl:text>[CR-DE-BT-06-Lot] The 'cbc:ProcurementTypeCode' element with a 'listName' attribute value of 'strategic-procurement' must exist within the 'cac:ProcurementAdditionalType'.
      </svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="($schxslt:patterns-matched, 'd15e244')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Part']/cac:ProcurementProject"
                  priority="16"
                  mode="d15e54">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd15e244']">
            <schxslt:rule pattern="d15e244">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Part']/cac:ProcurementProject" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Part']/cac:ProcurementProject</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d15e244">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Part']/cac:ProcurementProject</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(boolean(normalize-space(cbc:ProcurementTypeCode[@listName = 'contract-nature'])))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-23-Part">
                     <xsl:attribute name="test">boolean(normalize-space(cbc:ProcurementTypeCode[@listName = 'contract-nature']))</xsl:attribute>
                     <svrl:text>[CR-DE-BT-23-Part] cbc:ProcurementTypeCode must exist as child of <xsl:value-of select="name()"/>.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(boolean(normalize-space(cbc:Name)))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-21-Part">
                     <xsl:attribute name="test">boolean(normalize-space(cbc:Name))</xsl:attribute>
                     <svrl:text>[CR-DE-BT-23-Part] cbc:Name must exist as child of <xsl:value-of select="name()"/>.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="($schxslt:patterns-matched, 'd15e244')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Part']/cac:TenderingTerms/cac:CallForTendersDocumentReference"
                  priority="15"
                  mode="d15e54">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd15e244']">
            <schxslt:rule pattern="d15e244">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Part']/cac:TenderingTerms/cac:CallForTendersDocumentReference" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Part']/cac:TenderingTerms/cac:CallForTendersDocumentReference</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d15e244">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Part']/cac:TenderingTerms/cac:CallForTendersDocumentReference</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(           if ($SUBTYPE = $SUBTYPES-BT-708) then             (count(cbc:LanguageID) ge 1)           else             true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-708-Part">
                     <xsl:attribute name="test">           if ($SUBTYPE = $SUBTYPES-BT-708) then             (count(cbc:LanguageID) ge 1)           else             true()</xsl:attribute>
                     <svrl:text>[CR-DE-BT-708-Part] cbc:LanguageID must exist.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="($schxslt:patterns-matched, 'd15e244')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']/cac:TenderingTerms/cac:CallForTendersDocumentReference"
                  priority="14"
                  mode="d15e54">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd15e244']">
            <schxslt:rule pattern="d15e244">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']/cac:TenderingTerms/cac:CallForTendersDocumentReference" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']/cac:TenderingTerms/cac:CallForTendersDocumentReference</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d15e244">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']/cac:TenderingTerms/cac:CallForTendersDocumentReference</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(           if ($SUBTYPE = $SUBTYPES-BT-708) then             (count(cbc:LanguageID) ge 1)           else             true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-708-Lot">
                     <xsl:attribute name="test">           if ($SUBTYPE = $SUBTYPES-BT-708) then             (count(cbc:LanguageID) ge 1)           else             true()</xsl:attribute>
                     <svrl:text>[CR-DE-BT-708-Lot] cbc:LanguageID must exist.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="($schxslt:patterns-matched, 'd15e244')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot' or cbc:ID/@schemeName = 'Part']/cac:ProcurementProject/cac:RealizedLocation/cac:Address"
                  priority="13"
                  mode="d15e54">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd15e244']">
            <schxslt:rule pattern="d15e244">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot' or cbc:ID/@schemeName = 'Part']/cac:ProcurementProject/cac:RealizedLocation/cac:Address" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot' or cbc:ID/@schemeName = 'Part']/cac:ProcurementProject/cac:RealizedLocation/cac:Address</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d15e244">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot' or cbc:ID/@schemeName = 'Part']/cac:ProcurementProject/cac:RealizedLocation/cac:Address</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(count(cac:Country) = 1)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="SR-DE-15">
                     <xsl:attribute name="test">count(cac:Country) = 1</xsl:attribute>
                     <svrl:text>Every <xsl:value-of select="name()"/> has to have cac:Country</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(boolean(normalize-space(cac:Country/cbc:IdentificationCode)))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-5141">
                     <xsl:attribute name="test">boolean(normalize-space(cac:Country/cbc:IdentificationCode))</xsl:attribute>
                     <svrl:text>[CR-DE-BT-5141] cbc:IdentificationCode must exist.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(boolean(normalize-space(cbc:CountrySubentityCode)))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-5071">
                     <xsl:attribute name="test">boolean(normalize-space(cbc:CountrySubentityCode))</xsl:attribute>
                     <svrl:text>[CR-DE-BT-5071] cbc:CountrySubentityCode must exist.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="($schxslt:patterns-matched, 'd15e244')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="$EXTENSION-NODE/efac:NoticeResult/efac:GroupFramework/efbc:GroupFrameworkMaximumValueAmount"
                  priority="12"
                  mode="d15e54">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd15e244']">
            <schxslt:rule pattern="d15e244">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "$EXTENSION-NODE/efac:NoticeResult/efac:GroupFramework/efbc:GroupFrameworkMaximumValueAmount" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$EXTENSION-NODE/efac:NoticeResult/efac:GroupFramework/efbc:GroupFrameworkMaximumValueAmount</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d15e244">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$EXTENSION-NODE/efac:NoticeResult/efac:GroupFramework/efbc:GroupFrameworkMaximumValueAmount</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(false())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-156-NoticeResult">
                     <xsl:attribute name="test">false()</xsl:attribute>
                     <svrl:text>[CR-DE-BT-156-NoticeResult] efbc:GroupFrameworkMaximumValueAmount forbidden.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="($schxslt:patterns-matched, 'd15e244')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'LotsGroup']/cac:TenderingProcess/cac:FrameworkAgreement/cbc:EstimatedMaximumValueAmount"
                  priority="11"
                  mode="d15e54">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd15e244']">
            <schxslt:rule pattern="d15e244">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'LotsGroup']/cac:TenderingProcess/cac:FrameworkAgreement/cbc:EstimatedMaximumValueAmount" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'LotsGroup']/cac:TenderingProcess/cac:FrameworkAgreement/cbc:EstimatedMaximumValueAmount</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d15e244">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'LotsGroup']/cac:TenderingProcess/cac:FrameworkAgreement/cbc:EstimatedMaximumValueAmount</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(false())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-157">
                     <xsl:attribute name="test">false()</xsl:attribute>
                     <svrl:text>[CR-DE-BT-157] <xsl:value-of select="name()"/> is forbidden.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="($schxslt:patterns-matched, 'd15e244')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']/cac:TenderingTerms/cac:TendererQualificationRequest[not(cbc:CompanyLegalFormCode)]"
                  priority="10"
                  mode="d15e54">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd15e244']">
            <schxslt:rule pattern="d15e244">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']/cac:TenderingTerms/cac:TendererQualificationRequest[not(cbc:CompanyLegalFormCode)]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']/cac:TenderingTerms/cac:TendererQualificationRequest[not(cbc:CompanyLegalFormCode)]</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d15e244">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']/cac:TenderingTerms/cac:TendererQualificationRequest[not(cbc:CompanyLegalFormCode)]</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(           if ($SUBTYPE = $SUBTYPES-BT-771-772) then             (count(cac:SpecificTendererRequirement[not(cbc:TendererRequirementTypeCode[@listName = 'reserved-procurement'])]) = 1)           else             true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="SR-DE-20">
                     <xsl:attribute name="test">           if ($SUBTYPE = $SUBTYPES-BT-771-772) then             (count(cac:SpecificTendererRequirement[not(cbc:TendererRequirementTypeCode[@listName = 'reserved-procurement'])]) = 1)           else             true()</xsl:attribute>
                     <svrl:text>Every <xsl:value-of select="name()"/> has to have one cac:SpecificTendererRequirement</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="($schxslt:patterns-matched, 'd15e244')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']/cac:TenderingTerms/cac:TendererQualificationRequest[not(cbc:CompanyLegalFormCode)]/cac:SpecificTendererRequirement[not(cbc:TendererRequirementTypeCode[@listName = 'reserved-procurement'])]"
                  priority="9"
                  mode="d15e54">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd15e244']">
            <schxslt:rule pattern="d15e244">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']/cac:TenderingTerms/cac:TendererQualificationRequest[not(cbc:CompanyLegalFormCode)]/cac:SpecificTendererRequirement[not(cbc:TendererRequirementTypeCode[@listName = 'reserved-procurement'])]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']/cac:TenderingTerms/cac:TendererQualificationRequest[not(cbc:CompanyLegalFormCode)]/cac:SpecificTendererRequirement[not(cbc:TendererRequirementTypeCode[@listName = 'reserved-procurement'])]</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d15e244">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']/cac:TenderingTerms/cac:TendererQualificationRequest[not(cbc:CompanyLegalFormCode)]/cac:SpecificTendererRequirement[not(cbc:TendererRequirementTypeCode[@listName = 'reserved-procurement'])]</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(           if ($SUBTYPE = $SUBTYPES-BT-771-772) then             boolean(normalize-space(cbc:TendererRequirementTypeCode[@listName = 'missing-info-submission']))           else             true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-771-Lot">
                     <xsl:attribute name="test">           if ($SUBTYPE = $SUBTYPES-BT-771-772) then             boolean(normalize-space(cbc:TendererRequirementTypeCode[@listName = 'missing-info-submission']))           else             true()</xsl:attribute>
                     <svrl:text>[CR-DE-BT-771-Lot] cbc:TendererRequirementTypeCode must exist.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(           if ($SUBTYPE = $SUBTYPES-BT-771-772) then             boolean(normalize-space(cbc:Description))           else             true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       flag="fatal"
                                       id="CR-DE-BT-772-Lot">
                     <xsl:attribute name="test">           if ($SUBTYPE = $SUBTYPES-BT-771-772) then             boolean(normalize-space(cbc:Description))           else             true()</xsl:attribute>
                     <svrl:text>[CR-DE-BT-772-Lot] cbc:Description must exist.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="($schxslt:patterns-matched, 'd15e244')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']/cac:TenderingTerms"
                  priority="8"
                  mode="d15e54">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd15e244']">
            <schxslt:rule pattern="d15e244">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']/cac:TenderingTerms" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']/cac:TenderingTerms</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d15e244">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']/cac:TenderingTerms</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(           if ($SUBTYPE = $SUBTYPES-BT-97) then             exists(cac:Language/cbc:ID)           else             true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-97-Lot">
                     <xsl:attribute name="test">           if ($SUBTYPE = $SUBTYPES-BT-97) then             exists(cac:Language/cbc:ID)           else             true()</xsl:attribute>
                     <svrl:text>[CR-DE-BT-97-Lot] cac:Language/cbc:ID must exist for subtypes <xsl:value-of select="$SUBTYPES-BT-97"/>.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(           if ($SUBTYPE = $SUBTYPES-BT-15) then             (count(cac:CallForTendersDocumentReference[not(cbc:DocumentType/text() = 'restricted-document')]/cac:Attachment/cac:ExternalReference/cbc:URI) ge 1)           else             true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-15-Lot">
                     <xsl:attribute name="test">           if ($SUBTYPE = $SUBTYPES-BT-15) then             (count(cac:CallForTendersDocumentReference[not(cbc:DocumentType/text() = 'restricted-document')]/cac:Attachment/cac:ExternalReference/cbc:URI) ge 1)           else             true()</xsl:attribute>
                     <svrl:text>[CR-DE-BT-15-Lot] cbc:URI must exist.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="($schxslt:patterns-matched, 'd15e244')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Part']/cac:TenderingTerms"
                  priority="7"
                  mode="d15e54">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd15e244']">
            <schxslt:rule pattern="d15e244">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Part']/cac:TenderingTerms" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Part']/cac:TenderingTerms</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d15e244">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Part']/cac:TenderingTerms</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(           if ($SUBTYPE = $SUBTYPES-BT-15) then             (count(cac:CallForTendersDocumentReference[not(cbc:DocumentType/text() = 'restricted-document')]/cac:Attachment/cac:ExternalReference/cbc:URI) ge 1)           else             true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-15-Part">
                     <xsl:attribute name="test">           if ($SUBTYPE = $SUBTYPES-BT-15) then             (count(cac:CallForTendersDocumentReference[not(cbc:DocumentType/text() = 'restricted-document')]/cac:Attachment/cac:ExternalReference/cbc:URI) ge 1)           else             true()</xsl:attribute>
                     <svrl:text>[CR-DE-BT-15-Part] cbc:URI must exist.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="($schxslt:patterns-matched, 'd15e244')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Part']"
                  priority="6"
                  mode="d15e54">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd15e244']">
            <schxslt:rule pattern="d15e244">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Part']" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Part']</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d15e244">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Part']</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(           if ($SUBTYPE-CODE-NODE = $SUBTYPES-BT-726) then             (boolean(normalize-space(cac:ProcurementProject/cbc:SMESuitableIndicator)))           else             true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-726-Part">
                     <xsl:attribute name="test">           if ($SUBTYPE-CODE-NODE = $SUBTYPES-BT-726) then             (boolean(normalize-space(cac:ProcurementProject/cbc:SMESuitableIndicator)))           else             true()</xsl:attribute>
                     <svrl:text>[CR-DE-BT-726-Part] cbc:SMESuitableIndicator must exist.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="($schxslt:patterns-matched, 'd15e244')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'LotsGroup']"
                  priority="5"
                  mode="d15e54">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd15e244']">
            <schxslt:rule pattern="d15e244">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'LotsGroup']" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'LotsGroup']</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d15e244">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'LotsGroup']</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(boolean(normalize-space(cac:ProcurementProject/cbc:Name)))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-21-LotsGroup">
                     <xsl:attribute name="test">boolean(normalize-space(cac:ProcurementProject/cbc:Name))</xsl:attribute>
                     <svrl:text>[CR-DE-BT-21-LotsGroup] cac:ProcurementProject/cbc:Name must exist.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(           if ($SUBTYPE-CODE-NODE = $SUBTYPES-BT-726) then             (boolean(normalize-space(cac:ProcurementProject/cbc:SMESuitableIndicator)))           else             true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-726-LotsGroup">
                     <xsl:attribute name="test">           if ($SUBTYPE-CODE-NODE = $SUBTYPES-BT-726) then             (boolean(normalize-space(cac:ProcurementProject/cbc:SMESuitableIndicator)))           else             true()</xsl:attribute>
                     <svrl:text>[CR-DE-BT-726-LotsGroup] cac:ProcurementProject/cbc:SMESuitableIndicator must exist.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="($schxslt:patterns-matched, 'd15e244')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']"
                  priority="4"
                  mode="d15e54">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd15e244']">
            <schxslt:rule pattern="d15e244">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d15e244">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(           if ($SUBTYPE = $SUBTYPES-BT-63) then             boolean(normalize-space(cac:TenderingTerms/cbc:VariantConstraintCode))           else             true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       flag="fatal"
                                       id="CR-DE-BT-63-Lot">
                     <xsl:attribute name="test">           if ($SUBTYPE = $SUBTYPES-BT-63) then             boolean(normalize-space(cac:TenderingTerms/cbc:VariantConstraintCode))           else             true()</xsl:attribute>
                     <svrl:text>[CR-DE-BT-63-Lot](<xsl:value-of select="$SUBTYPE"/>) cbc:VariantConstraintCode must exist in subtypes <xsl:value-of select="$SUBTYPES-BT-63"/>
                     </svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(           if ($SUBTYPE = $SUBTYPES-BT-17) then             boolean(normalize-space(cac:TenderingProcess/cbc:SubmissionMethodCode[@listName = 'esubmission']))           else             true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       flag="fatal"
                                       id="CR-DE-BT-17-Lot">
                     <xsl:attribute name="test">           if ($SUBTYPE = $SUBTYPES-BT-17) then             boolean(normalize-space(cac:TenderingProcess/cbc:SubmissionMethodCode[@listName = 'esubmission']))           else             true()</xsl:attribute>
                     <svrl:text>[CR-DE-BT-17-Lot](<xsl:value-of select="$SUBTYPE"/>) cbc:SubmissionMethodCode must exist in subtypes <xsl:value-of select="$SUBTYPES-BT-17"/>.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(           if ($SUBTYPE = $SUBTYPES-BT-769) then             boolean(normalize-space(cac:TenderingTerms/cbc:MultipleTendersCode))           else             true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-769-Lot">
                     <xsl:attribute name="test">           if ($SUBTYPE = $SUBTYPES-BT-769) then             boolean(normalize-space(cac:TenderingTerms/cbc:MultipleTendersCode))           else             true()</xsl:attribute>
                     <svrl:text>[CR-DE-BT-769-Lot](<xsl:value-of select="$SUBTYPE"/>)cbc:MultipleTendrersCode must exist in subtypes <xsl:value-of select="$SUBTYPES-BT-769"/>
                     </svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(           if ($SUBTYPE-CODE-NODE = $SUBTYPES-BT-726) then             (boolean(normalize-space(cac:ProcurementProject/cbc:SMESuitableIndicator)))           else             true())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-726-Lot">
                     <xsl:attribute name="test">           if ($SUBTYPE-CODE-NODE = $SUBTYPES-BT-726) then             (boolean(normalize-space(cac:ProcurementProject/cbc:SMESuitableIndicator)))           else             true()</xsl:attribute>
                     <svrl:text>[CR-DE-BT-726-Lot] cbc:SMESuitableIndicator must exist.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="($schxslt:patterns-matched, 'd15e244')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="$NOTICE_RESULT/efac:LotTender/efac:Origin/efbc:AreaCode"
                  priority="3"
                  mode="d15e54">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd15e244']">
            <schxslt:rule pattern="d15e244">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "$NOTICE_RESULT/efac:LotTender/efac:Origin/efbc:AreaCode" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$NOTICE_RESULT/efac:LotTender/efac:Origin/efbc:AreaCode</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d15e244">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$NOTICE_RESULT/efac:LotTender/efac:Origin/efbc:AreaCode</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(false())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-191">
                     <xsl:attribute name="test">false()</xsl:attribute>
                     <svrl:text>[CR-DE-BT-191 ]efbc:AreaCode forbidden.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="($schxslt:patterns-matched, 'd15e244')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="$ROOT-NODE/cac:TenderingTerms/cac:LotDistribution/cac:LotsGroup/cbc:LotsGroupID"
                  priority="2"
                  mode="d15e54">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd15e244']">
            <schxslt:rule pattern="d15e244">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "$ROOT-NODE/cac:TenderingTerms/cac:LotDistribution/cac:LotsGroup/cbc:LotsGroupID" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:TenderingTerms/cac:LotDistribution/cac:LotsGroup/cbc:LotsGroupID</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d15e244">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:TenderingTerms/cac:LotDistribution/cac:LotsGroup/cbc:LotsGroupID</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(false())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-330-Procedure">
                     <xsl:attribute name="test">false()</xsl:attribute>
                     <svrl:text>[CR-DE-BT-330-Procedure] cbc:LotsGroupID forbidden.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="($schxslt:patterns-matched, 'd15e244')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="$ROOT-NODE/cac:TenderingTerms/cac:LotDistribution/cac:LotsGroup/cac:ProcurementProjectLotReference/cbc:ID[@schemeName = 'Lot']"
                  priority="1"
                  mode="d15e54">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd15e244']">
            <schxslt:rule pattern="d15e244">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "$ROOT-NODE/cac:TenderingTerms/cac:LotDistribution/cac:LotsGroup/cac:ProcurementProjectLotReference/cbc:ID[@schemeName = 'Lot']" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:TenderingTerms/cac:LotDistribution/cac:LotsGroup/cac:ProcurementProjectLotReference/cbc:ID[@schemeName = 'Lot']</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d15e244">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$ROOT-NODE/cac:TenderingTerms/cac:LotDistribution/cac:LotsGroup/cac:ProcurementProjectLotReference/cbc:ID[@schemeName = 'Lot']</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(false())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-1375-Procedure">
                     <xsl:attribute name="test">false()</xsl:attribute>
                     <svrl:text>[CR-DE-BT-1375-Procedure] cbc:ID forbidden.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="($schxslt:patterns-matched, 'd15e244')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="$EXTENSION-NODE/efac:NoticeResult/efac:GroupFramework/efac:TenderLot/cbc:ID"
                  priority="0"
                  mode="d15e54">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd15e244']">
            <schxslt:rule pattern="d15e244">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "$EXTENSION-NODE/efac:NoticeResult/efac:GroupFramework/efac:TenderLot/cbc:ID" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$EXTENSION-NODE/efac:NoticeResult/efac:GroupFramework/efac:TenderLot/cbc:ID</xsl:attribute>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d15e244">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">$EXTENSION-NODE/efac:NoticeResult/efac:GroupFramework/efac:TenderLot/cbc:ID</xsl:attribute>
               </svrl:fired-rule>
               <xsl:if test="not(false())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                       location="{schxslt:location(.)}"
                                       role="error"
                                       id="CR-DE-BT-556-NoticeResult">
                     <xsl:attribute name="test">false()</xsl:attribute>
                     <svrl:text>[CR-DE-BT-556-NoticeResult] cbc:ID forbidden.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                                as="xs:string*"
                                select="($schxslt:patterns-matched, 'd15e244')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:function name="schxslt:location" as="xs:string">
      <xsl:param name="node" as="node()"/>
      <xsl:variable name="segments" as="xs:string*">
         <xsl:for-each select="($node/ancestor-or-self::node())">
            <xsl:variable name="position">
               <xsl:number level="single"/>
            </xsl:variable>
            <xsl:choose>
               <xsl:when test=". instance of element()">
                  <xsl:value-of select="concat('Q{', namespace-uri(.), '}', local-name(.), '[', $position, ']')"/>
               </xsl:when>
               <xsl:when test=". instance of attribute()">
                  <xsl:value-of select="concat('@Q{', namespace-uri(.), '}', local-name(.))"/>
               </xsl:when>
               <xsl:when test=". instance of processing-instruction()">
                  <xsl:value-of select="concat('processing-instruction(&#34;', name(.), '&#34;)[', $position, ']')"/>
               </xsl:when>
               <xsl:when test=". instance of comment()">
                  <xsl:value-of select="concat('comment()[', $position, ']')"/>
               </xsl:when>
               <xsl:when test=". instance of text()">
                  <xsl:value-of select="concat('text()[', $position, ']')"/>
               </xsl:when>
               <xsl:otherwise/>
            </xsl:choose>
         </xsl:for-each>
      </xsl:variable>
      <xsl:value-of select="concat('/', string-join($segments, '/'))"/>
   </xsl:function>
</xsl:transform>
