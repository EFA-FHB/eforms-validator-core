<pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="conditional-mandatory">
  <let name="SUBTYPES-BT-5071-BT-5141-Lot"
    value="('E2', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', 'E3', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30', '31', '32', 'E4', '33', '34', '35', '36', '37', '38', '39', '40')" />
  <let name="SUBTYPES-BT-5071-BT-5141-Part" value="('4', '5', '6')" />

  <let name="COUNTRIES-WITH-NUTS"
    value="('BEL', 'BGR', 'CZE', 'DNK', 'DEU', 'EST', 'IRL', 'GRC', 'ESP', 'FRA', 'HRV', 'ITA', 'CYP', 'LVA', 'LTU', 'LUX', 'HUN', 'MLT', 'NLD', 'AUT', 'POL', 'PRT', 'ROU', 'SVN', 'SVK', 'FIN', 'SWE', 'GBR', 'ISL', 'LIE', 'NOR', 'CHE', 'MNE', 'MKD', 'ALB', 'SRB', 'TUR')"/>

  <rule
    context="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']/cac:ProcurementProject/cac:ProcurementAdditionalType">


    <!-- bt-06 strategische beschaffung: cac:ProcurementAdditionalType/cbc:ProcurementTypeCode[@listName = 'strategic-procurement'] -->
    <!-- bt-774 grüne beschaffung:  cac:ProcurementAdditionalType/cbc:ProcurementTypeCode[@listName='environmental-impact']-->
    <!-- bt-775 soziale beschaffung: cac:ProcurementAdditionalType/cbc:ProcurementTypeCode[@listName='social-objective'] -->
    <!-- bt-776 bescahffung von innovation: cac:ProcurementAdditionalType/cbc:ProcurementTypeCode[@listName='innovative-acquisition']  -->
    <assert id="BR-DE-20" test="
        if (($SUBTYPE = $SUBTYPES-BT-06) and (cbc:ProcurementTypeCode[@listName = 'strategic-procurement']/text() != 'none'))
        then
          boolean(../cac:ProcurementAdditionalType/cbc:ProcurementTypeCode[@listName = 'environmental-impact'])
          or boolean(../cac:ProcurementAdditionalType/cbc:ProcurementTypeCode[@listName = 'social-objective'])
          or boolean(../cac:ProcurementAdditionalType/cbc:ProcurementTypeCode[@listName = 'innovative-acquisition'])
        else
          true()" role="error">[BR-DE-20] If a strategic-procurement value other then 'none'
      exists, the /cac:ProcurementAdditionalType/cbc:ProcurementTypeCode with corresponding listName
      must exist. </assert>

  </rule>


  <!-- all countries according to ted
    ('1A0', 'ABW', 'AFG', 'AGO', 'AIA', 'ALA', 'ALB', 'AND', 'ARE', 'ARG', 'ARM', 'ASM', 'ATA', 'ATG', 'AUS', 'AUT', 'AZE', 'BDI', 'BEL', 'BEN', 'BES', 'BFA', 'BGD', 'BGR', 'BHR', 'BHS', 'BIH', 'BLM', 'BLR', 'BLZ', 'BMU', 'BOL', 'BRA', 'BRB', 'BRN', 'BTN', 'BVT', 'BWA', 'CAF', 'CAN', 'CCK', 'CHE', 'CHL', 'CHN', 'CIV', 'CMR', 'COD', 'COG', 'COK', 'COL', 'COM', 'CPT', 'CPV', 'CRI', 'CUB', 'CUW', 'CXR', 'CYM', 'CYP', 'CZE', 'DEU', 'DJI', 'DMA', 'DNK', 'DOM', 'DZA', 'ECU', 'EGY', 'ERI', 'ESH', 'ESP', 'EST', 'ETH', 'FIN', 'FJI', 'FLK', 'FRA', 'FRO', 'FSM', 'GAB', 'GBR', 'GEO', 'GGY', 'GHA', 'GIB', 'GIN', 'GMB', 'GNB', 'GNQ', 'GRC', 'GRD', 'GRL', 'GTM', 'GUM', 'HKG', 'HMD', 'HND', 'HRV', 'HTI', 'HUN', 'IDN', 'IMN', 'IND', 'IOT', 'IRL', 'IRN', 'IRQ', 'ISL', 'ISR', 'ITA', 'JAM', 'JEY', 'JOR', 'JPN', 'KAZ', 'KEN', 'KGZ', 'KHM', 'KIR', 'KNA', 'KOR', 'KWT', 'LAO', 'LBN', 'LBR', 'LBY', 'LCA', 'LIE', 'LKA', 'LSO', 'LTU', 'LUX', 'LVA', 'MAC', 'MAF', 'MAR', 'MCO', 'MDA', 'MDG', 'MDV', 'MEX', 'MHL', 'MKD', 'MLI', 'MLT', 'MMR', 'MNE', 'MNG', 'MNP', 'MOZ', 'MRT', 'MSR', 'MUS', 'MWI', 'MYS', 'NAM', 'NCL', 'NER', 'NFK', 'NGA', 'NIC', 'NIU', 'NLD', 'NOR', 'NPL', 'NRU', 'NZL', 'OMN', 'PAK', 'PAN', 'PCN', 'PER', 'PHL', 'PLW', 'PNG', 'POL', 'PRI', 'PRK', 'PRT', 'PRY', 'PSE', 'PYF', 'QAT', 'ROU', 'RUS', 'RWA', 'SAU', 'SDN', 'SEN', 'SGP', 'SGS', 'SHN', 'SJM', 'SLB', 'SLE', 'SLV', 'SMR', 'SOM', 'SPM', 'SRB', 'SSD', 'STP', 'SUR', 'SVK', 'SVN', 'SWE', 'SWZ', 'SXM', 'SYC', 'SYR', 'TCA', 'TCD', 'TGO', 'THA', 'TJK', 'TKL', 'TKM', 'TLS', 'TON', 'TTO', 'TUN', 'TUR', 'TUV', 'TWN', 'TZA', 'UGA', 'UKR', 'UMI', 'URY', 'USA', 'UZB', 'VAT', 'VCT', 'VEN', 'VGB', 'VIR', 'VNM', 'VUT', 'WLF', 'WSM', 'YEM', 'ZAF', 'ZMB', 'ZWE')
   -->
  <!-- countries with nuts codes according to EU regulators 
    alpha-2-codes: BE,BG,CZ,DK,DE,EE,IE,EL,ES,FR,HR,IT,CY,LV,LT,LU,HU,MT,NL,AT,PL,PT,RO,SI,SK,FI,SE,UK,IS,LI,NO,CH,ME,MK,AL,RS,TR
    alpha-3-codes: 'BEL', 'BGR', 'CZE', 'DNK', 'DEU', 'EST', 'IRL', 'GRC', 'ESP', 'FRA', 'HRV', 'ITA', 'CYP', 'LVA', 'LTU', 'LUX', 'HUN', 'MLT', 'NLD', 'AUT', 'POL', 'PRT', 'ROU', 'SVN', 'SVK', 'FIN', 'SWE', 'GBR', 'ISL', 'LIE', 'NOR', 'CHE', 'MNE', 'MKD', 'ALB', 'SRB', 'TUR' 
    -->

  <!-- 
    Erfüllungsort 
    BT-5071: Procedure, Lot, Part
        Procedure:   /*/cac:ProcurementProject/cac:RealizedLocation/cac:Address/cbc:CountrySubentityCode | forbidden: never
        Lot:         /*/cac:ProcurementProjectLot[cbc:ID/@schemeName='Lot']/cac:ProcurementProject/cac:RealizedLocation/cac:Address/cbc:CountrySubentityCode
        Part:        /*/cac:ProcurementProjectLot[cbc:ID/@schemeName='Part']/cac:ProcurementProject/cac:RealizedLocation/cac:Address/cbc:CountrySubentityCode 
    
    Erfüllungsort — Ländercode
    BT-5141: Procedure, Lot, Part
        Procedure:  /*/cac:ProcurementProject/cac:RealizedLocation/cac:Address/cac:Country/cbc:IdentificationCode | forbidden: never
        Lot:        /*/cac:ProcurementProjectLot[cbc:ID/@schemeName='Lot']/cac:ProcurementProject/cac:RealizedLocation/cac:Address/cac:Country/cbc:IdentificationCode
        Part:       /*/cac:ProcurementProjectLot[cbc:ID/@schemeName='Part']/cac:ProcurementProject/cac:RealizedLocation/cac:Address/cac:Country/cbc:IdentificationCode
    -->
  <!--
    Country Subdivision (nuts-code)
    BT-507: UBO, Business, Organization-Company, Organization-TouchPoint
        - WIRD NICHT UMGESETZT, da nicht anwendbar. UBO:                    /*/ext:UBLExtensions/ext:UBLExtension/ext:ExtensionContent/efext:EformsExtension/efac:Organizations/efac:UltimateBeneficialOwner/cac:ResidenceAddress/cbc:CountrySubentityCode
        Organization-Company:   /*/ext:UBLExtensions/ext:UBLExtension/ext:ExtensionContent/efext:EformsExtension/efac:Organizations/efac:Organization/efac:Company/cac:PostalAddress/cbc:CountrySubentityCode | forbidden: never
        Organization-Touchpoint:/*/ext:UBLExtensions/ext:UBLExtension/ext:ExtensionContent/efext:EformsExtension/efac:Organizations/efac:Organization/efac:TouchPoint/cac:PostalAddress/cbc:CountrySubentityCode | forbidden: never 
        -X xml gibt's nicht: Business: /*/cac:BusinessParty/cac:PostalAddress/cbc:CountrySubentityCode
    
    Country Code
    BT-514: UBO, Business, Organization-Company, Organization-TouchPoint
        - WIRD NICHT UMGESETZT, da nicht anwendbar. UBO:                    /*/ext:UBLExtensions/ext:UBLExtension/ext:ExtensionContent/efext:EformsExtension/efac:Organizations/efac:UltimateBeneficialOwner/cac:ResidenceAddress/cac:Country/cbc:IdentificationCode
        regeln nicht nötig, weil Country/Identificationcode eh verpflichtend - Organization-Company:   /*/ext:UBLExtensions/ext:UBLExtension/ext:ExtensionContent/efext:EformsExtension/efac:Organizations/efac:Organization/efac:Company/cac:PostalAddress/cac:Country/cbc:IdentificationCode | forbidden: never
        regeln nicht nötig, weil Country/Identificationcode eh verpflichtend - Organization-Touchpoint:/*/ext:UBLExtensions/ext:UBLExtension/ext:ExtensionContent/efext:EformsExtension/efac:Organizations/efac:Organization/efac:TouchPoint/cac:PostalAddress/cac:Country/cbc:IdentificationCode | forbidden: never
        -X xml gibt's nicht: Business: /*/cac:BusinessParty/cac:PostalAddress/cac:Country/cbc:IdentificationCode
    -->


  <rule
    context="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = ('Lot', 'Part')]/cac:ProcurementProject/cac:RealizedLocation/cac:Address">
    <assert id="BR-DE-27-Lot" test="
        if ((normalize-space(cac:Country/cbc:IdentificationCode/text()) = $COUNTRIES-WITH-NUTS) and ($SUBTYPE = $SUBTYPES-BT-5071-BT-5141-Lot)) then
          boolean(normalize-space(cbc:CountrySubentityCode))
        else
          true()" role="warning">[BR-DE-27-Lot] In Countries where NUTS-Codes exist,
      cbc:CountrySubentityCode (BT-5071) is mandatory. </assert>

    <assert id="BR-DE-27-Part" test="
        if ((normalize-space(cac:Country/cbc:IdentificationCode/text()) = $COUNTRIES-WITH-NUTS and ($SUBTYPE = $SUBTYPES-BT-5071-BT-5141-Part))) then
          boolean(normalize-space(cbc:CountrySubentityCode))
        else
          true()" role="warning">[BR-DE-27-Part] In Countries where NUTS-Codes exist,
      cbc:CountrySubentityCode (BT-5071) is mandatory. </assert>
  </rule>

  <rule context="$ROOT-NODE/cac:ProcurementProject/cac:RealizedLocation/cac:Address">
    <assert id="BR-DE-27-Procedure" test="
        if (normalize-space(cac:Country/cbc:IdentificationCode/text()) = $COUNTRIES-WITH-NUTS) then
          boolean(normalize-space(cbc:CountrySubentityCode))
        else
          true()" role="warning">[BR-DE-27-Procedure] In Countries where NUTS-Codes exist,
      cbc:CountrySubentityCode (BT-5071) is mandatory. </assert>
  </rule>

  <rule
    context="($EXTENSION-ORG-NODE/efac:TouchPoint union $EXTENSION-ORG-NODE/efac:Company)/cac:PostalAddress">
    <assert id="BR-DE-28-Company-Touchpoint" test="
        if (normalize-space(cac:Country/cbc:IdentificationCode/text()) = $COUNTRIES-WITH-NUTS) then
          boolean(normalize-space(cbc:CountrySubentityCode))
        else
          true()" role="warning">[BR-DE-28-Company-Touchpoint] In Countries where NUTS-Codes
      exist, cbc:CountrySubentityCode (BT-507) is mandatory. </assert>
  </rule>


  <let name="temp"
    value="tokenize($ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = ('Part', 'Lot', 'LotsGroup')]/cac:ProcurementProject/cbc:Note/text(), ',')" />

  <rule
    context="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = ('Part', 'Lot', 'LotsGroup')]/cac:ProcurementProject/cbc:Note">


    <!-- bt-726 true, then bt-300 must start with  #Besonders auch geeignet für:{freelance|selbst|startup|other-sme}# -->
    <!-- bt-726 exists in Lot, LotsGroup and Part
        Lot /*/cac:ProcurementProjectLot[cbc:ID/@schemeName='Lot']/cac:ProcurementProject/cbc:SMESuitableIndicator
        Part /*/cac:ProcurementProjectLot[cbc:ID/@schemeName='Part']/cac:ProcurementProject/cbc:SMESuitableIndicator 
        -->
    <!-- bt-300 exists in Lot, LotsGroup, Part and Procedure,  Procedure not relevant as SMESuitableIndicator doesn't exist there
        Lot /*/cac:ProcurementProjectLot[cbc:ID/@schemeName='Lot']/cac:ProcurementProject/cbc:Note
        Part /*/cac:ProcurementProjectLot[cbc:ID/@schemeName='Part']/cac:ProcurementProject/cbc:Note
       -->

    <!-- 
      erlaubte werte ergänzen um: '#Besonders auch geeignet für:other-sme#'
      mehrere werte müssen möglich sein und in EFX funktionieren 
   #Besonders auch geeignet für:freelance#, #Besonders auch geeignet für:startup#, #Besonders auch geeignet für:other-sme#, #Besonders auch geeignet für:selbst#
      oder eine beliebige kombination der 4 
      -->

    <!-- xmute test are in: src/test/conditional-mandatory/eforms_CN_16_language_{DEU,DEU+ENG,DEU}.xml (FRA as main language and DEU in addition)
* 
     -->
    <let name="IS-SME-SUITABLE"
      value="../cbc:SMESuitableIndicator/text() = ('true', '1')" />
    <let name="NOTE-TEXT" value="normalize-space(.)" />

    <!-- whatever the main language is, it must have the additional SME information -->
    <assert id="BR-DE-26" test="
        if ($IS-SME-SUITABLE and @languageID = $MAIN-LANG) then
          starts-with($NOTE-TEXT, '#')
        else
          true()">[BR-DE-26] If SMESuitableIndicator is true, BT-300 /cac:ProcurementProject/cbd:Note text must start with #. But it is <value-of select="$NOTE-TEXT" /></assert>
    <!-- any note with in German then the following regex applies to each token after #-->
    <let name="CODE-REGEX" value="'(freelance|startup|other-sme|selbst)$'" />
    <let name="DEU-REGEX"
      value="concat('^Besonders auch geeignet für:', $CODE-REGEX)" />

    <assert id="BR-DE-26-DEU" test="
        if ($IS-SME-SUITABLE and @languageID = 'DEU')
        then
          every $block in tokenize($NOTE-TEXT, '#')[position() mod 2 = 0]
            satisfies
            matches(normalize-space($block), $DEU-REGEX)
        else
          true()
        " role="error">[BR-DE-26-DEU] If SMESuitableIndicator is true, BT-300 /cac:ProcurementProject/cbd:Note needs to start with a text-block of the form "#<value-of select="$DEU-REGEX" />#" (without quotes). E.g "#Besonders auch geeignet für:startup#", a sequence of comma separated text-blocks can be used, free-text can follow. But it is <value-of select="$NOTE-TEXT" /></assert>

    <!-- if main-lang is NOT German then prefix is not determined -->
    <let name="OTHER-LANG-REGEX" value="concat('.+?:', $CODE-REGEX)" />
    <assert id="BR-DE-26-other-main-lang" test="
        if ($IS-SME-SUITABLE and not(@languageID = 'DEU'))
        then
          every $block in tokenize($NOTE-TEXT, '#')[position() mod 2 = 0]
            satisfies
            matches(normalize-space($block), $OTHER-LANG-REGEX)
        else
          true()
        " role="error">[BR-DE-26-other-main-lang] If SMESuitableIndicator is true, BT-300 /cac:ProcurementProject/cbd:Note needs to start with a text-block of the form "<value-of select="$OTHER-LANG-REGEX" />" (without quotes). E.g "#Also suitable for:startup#", a sequence of comma separated text-blocks can be used, free-text can follow. But it is <value-of select="$NOTE-TEXT" /></assert>

    <!--    <assert id="t1" test="false()">
Language is <value-of select="@languageID" />
German?=<value-of select="not(@languageID = 'DEU')" />
SME=<value-of select="$IS-SME-SUITABLE" />
<value-of select="tokenize(., '#')[position() mod 2 = 0]" />
note text=<value-of select="$NOTE-TEXT" />
</assert>-->

  </rule>




  <!-- BR-DE-24: Wenn die Auftragsvergabe in den Anwendungsbereich des Saubere-Fahrzeuge-Beschaffungs-Gesetzes
 zur Umsetzung der Richtlinie 2009/33/EG fällt, müssen die BTs der BG-714 
und BG-7141 ( BT-735, BT-723, BT-715, BT-725, BT-716) nach Maßgabe ihrer 
jeweiligen Beschreibungen übermittelt werden.
 -->

  <!-- it all starts with BT-717 if it is CVD related -->


  <!-- now issue is that the BG-714 with the only BT-735 is 
    BT-735-LotResult 
    /*/ext:UBLExtensions/ext:UBLExtension/ext:ExtensionContent/efext:EformsExtension/efac:NoticeResult/efac:LotResult/efac:StrategicProcurement/efac:StrategicProcurementInformation/efbc:ProcurementCategoryCode 

    OR 

    BT-735-Lot:
    /*/cac:ProcurementProjectLot[cbc:ID/@schemeName='Lot']/cac:Tendering-Terms/ext:UBLExtensions/ext:UBLExtension/ext:ExtensionContent/
     efext:EformsExtension/efac:StrategicProcurement/efac:StrategicProcurementInformation/efbc:ProcurementCategoryCode

    All the Rest is in LotResult.

    Because also ted example  https://github.com/OP-TED/eForms-SDK/blob/1.7.0/examples/notices/can_24_maximal.xml uses BT-735 in both places, we will check for this with AND logic. 

    More precisely this rule checks on ALL Bt-717 in TenderingTerms and next rule on those LotResult which have same id as this TenderingTerms.

    Leaving it open if one place will be skipped later. See also https://github.com/OP-TED/eForms-SDK/discussions/596

 -->

  <let name="LOT-STRATEGIC-PROCURMENT"
    value="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = 'Lot']/cac:TenderingTerms/ext:UBLExtensions/ext:UBLExtension/ext:ExtensionContent/efext:EformsExtension/efac:StrategicProcurement"/>

  <let name="LOT-RESULT" value="$EXTENSION-NODE/efac:NoticeResult/efac:LotResult"/>

  <!-- context: Match BT-717. Mandatory is checked by cardinality rule: CR-DE-BT-717-Lot  -->
  <rule
    context="$LOT-STRATEGIC-PROCURMENT[efbc:ApplicableLegalBasis[@listName = 'cvd-scope']/text() = 'true']"
    id="BR-DE-24-CM-BT-735">

    <assert id="BT-735-Lot"
      test="boolean(normalize-space(efac:StrategicProcurementInformation/efbc:ProcurementCategoryCode[@listName = 'cvd-contract-type']))"
      role="error">[BT-735-Lot] BT-735 must exist if BT-717=true</assert>

  </rule>

  <!-- 
context: Matching those efac:StrategicProcurementInformation in LotResults with efac:TenderLot/cbc:ID = cac:ProcurementProjectLot/cbc:ID where BT-717-MATCH-ID 
-->
  <let name="BT-717-MATCH-ID"
    value="$ROOT-NODE/cac:ProcurementProjectLot[cac:TenderingTerms/ext:UBLExtensions/ext:UBLExtension/ext:ExtensionContent/efext:EformsExtension/efac:StrategicProcurement/efbc:ApplicableLegalBasis[@listName = 'cvd-scope']/text() = 'true']/cbc:ID"/>

  <rule context="
      $LOT-RESULT[
      efac:TenderLot/cbc:ID = $BT-717-MATCH-ID
      ]/efac:StrategicProcurement/efac:StrategicProcurementInformation" id="BR-DE-24-stats">

    <!-- Check in addition if BT-735 is also present in this LotResult-->
    <assert id="BT-735-LotResult"
      test="boolean(normalize-space(efbc:ProcurementCategoryCode[@listName = 'cvd-contract-type']))"
      role="error">[BT-735-LotResult] BT-735-LotResult must exist if BT-717=true</assert>

    <let name="BT-723"
      value="efac:ProcurementDetails/efbc:AssetCategoryCode[@listName = 'vehicle-category']"/>

    <assert id="BR-DE-24-BT-723" test="exists($BT-723)" role="error">[BR-DE-24-BT-723] Statistics on
      at least one vehicle-category must exist here and in LotResult.</assert>

    <assert id="BR-DE-24-distinct" test="count($BT-723) = count(distinct-values($BT-723))" role="error">[BR-DE-24-distinct] Each vehicle category must be reported only once. But
        <value-of select="
          for $vcat in distinct-values($BT-723)
          return
            concat($vcat, ' appears ', count($BT-723[. = $vcat]))"/></assert>

  </rule>

  <rule context="
      $LOT-RESULT[
      efac:TenderLot/cbc:ID = $BT-717-MATCH-ID
      ]/efac:StrategicProcurement/efac:StrategicProcurementInformation/efac:ProcurementDetails">

    <let name="STATS" value="efac:StrategicProcurementStatistics"/>
    <assert id="BR-DE-24-BT-715"
      test="$STATS[efbc:StatisticsCode[. = 'vehicles']]/efbc:StatisticsNumeric[xs:integer(.) ge 0]"
      role="error">[BR-DE-24-BT-715] Statistics on BT-715: vehicles must exist and values must be
      &gt;= 0</assert>

    <assert id="BR-DE-24-BT-725"
      test="$STATS[efbc:StatisticsCode[. = 'vehicles-zero-emission']]/efbc:StatisticsNumeric[xs:integer(.) ge 0]"
      role="error">[BR-DE-24-BT-725] Statistics on BT-725: vehicles-zero-submission must exist and
      values must be &gt;= 0</assert>


    <assert id="BR-DE-24-BT-716"
      test="$STATS[efbc:StatisticsCode[. = 'vehicles-clean']]/efbc:StatisticsNumeric[xs:integer(.) ge 0]"
      role="error">[BR-DE-24-BT-716] Statistics on BT-725: vehicles-zero-submission must exist and
      values must be &gt;= 0</assert>
  </rule>

  <!-- BR-DE-23 
    BT-541 with value >= 10% requires 
      BT-539  AwardingCriterionTypeCode EU: optional
      BT-734  Name                      EU: optional
      BT-541  ParameterNumeric          EU: mandatory already   Lot, Lotsgroup
      BT-5421 ParameterCode             EU: mandatory already
    to be filled

    possible parametercode listnames: 
      number-weight (BT-5421) - decimal: dec-exa, dec-mid | order of importance: ord-imp | percentage: per-exa(exact), per-mid(middle of range) | points: poi-exa, poi-mid
      number-fixed (BT-5422) - fix-tot, fix-unit
      number-threshold (BT-5423) - min-score, max-pass

    only per-exa get's checked, as all others are not exact or not percentages
 -->

  <rule
    context="$ROOT-NODE/cac:ProcurementProjectLot[cbc:ID/@schemeName = ('Lot', 'Part')]/cac:TenderingTerms/cac:AwardingTerms/cac:AwardingCriterion/cac:SubordinateAwardingCriterion">

    <let name="AwardCriterionParameter"
      value="ext:UBLExtensions/ext:UBLExtension/ext:ExtensionContent/efext:EformsExtension/efac:AwardCriterionParameter"/>

    <assert id="BR-DE-23" test="
        if
        (normalize-space($AwardCriterionParameter/efbc:ParameterCode/text()) = 'per-exa' and
        number(normalize-space($AwardCriterionParameter/efbc:ParameterNumeric/text())) ge 10
        )
        then
          (boolean(normalize-space(cbc:AwardingCriterionTypeCode)) and
          boolean(normalize-space(cbc:Name[./@languageID = $MAIN-LANG]))
          )
        else
          true()" role="error">[BR-DE-23] When a percentage value (ParameterCode per-exa) in
      ParameterNumeric has a value >= 10 then cbc:AwardingCriterionTypeCode and cbc:Name with
      attribute languageID="<value-of select="$MAIN-LANG" />" are mandatory. </assert>
  </rule>





</pattern>
