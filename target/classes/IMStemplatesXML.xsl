<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
 <xsl:template match="/">
	<xsl:apply-templates select="ematrix"/>
 </xsl:template>
 
  <xsl:template match="ematrix">
	<html>
	<body>
	<h2>Data from XML</h2>
            <xsl:apply-templates select="attributeDef"/>
            <xsl:apply-templates select="type"/>            
            <xsl:apply-templates select="policy"/>
            <xsl:apply-templates select="relationshipDef"/>
            <xsl:apply-templates select="accessRule"/>
            <xsl:apply-templates select="interfaceType"/>
            <xsl:apply-templates select="expressionObject"/>
            <xsl:apply-templates select="role"/>
            <xsl:apply-templates select="group"/>
            <xsl:apply-templates select="person"/>
            <xsl:apply-templates select="association"/>
            <xsl:apply-templates select="program"/>
            <xsl:apply-templates select="format"/>
            <xsl:apply-templates select="page"/>
            <xsl:apply-templates select="dimension"/>
	</body>
	</html>
 </xsl:template>
 
 <!-- Attribute definition -->
  <xsl:template match="attributeDef">
    <h3>Attribute</h3>
    <b>ID: </b><xsl:value-of select="@id"/><br />
    <xsl:apply-templates select="adminProperties"/>
    <b>Attribute Type: </b><xsl:value-of select="primitiveType"/><br />
    <xsl:if test='accessRuleRef'>
        <b>Rule: </b><xsl:value-of select="accessRuleRef"/><br />
    </xsl:if>
    <xsl:if test='multiline'>
        multiline<br />
    </xsl:if>
    <xsl:if test='resetonclone'>
        resetonclone<br />
    </xsl:if>
    <xsl:if test='resetonrevision'>
        resetonrevision<br />
    </xsl:if>
    <b>Max Length: </b><xsl:value-of select="maxlength"/> <br />
    <xsl:if test='defaultValue'>
        <b>Default Value: </b><xsl:value-of select="defaultValue"/><br />
    </xsl:if>
    <xsl:apply-templates select="rangeList"/>
    <xsl:apply-templates select="rangeProgram"/><br />
    <xsl:apply-templates select="triggerList"/>
    <xsl:if test='dimensionRef'>
        <b>Dimension: </b><xsl:value-of select="dimensionRef"/> <br />
    </xsl:if>
    <b>Value Type: </b><xsl:value-of select="attrValueType"/> <br />
</xsl:template>

 <!-- Type definition-->
 <xsl:template match="type">
    <h3>Type</h3>
    <b>ID: </b><xsl:value-of select="@id"/><br />
    <xsl:apply-templates select="adminProperties"/>
    <xsl:if test='abstract'>
        abstract<br />
    </xsl:if>
    <xsl:apply-templates select="derivedFrom"/><br />
    <xsl:apply-templates select="attributeDefRefList"/> <br /> 
    <xsl:apply-templates select="methodList"/> <br />
    <xsl:apply-templates select="triggerList"/> <br />
    <xsl:apply-templates select="localAttributes"/><br />
    <b>Type Kind: </b><xsl:value-of select="typeKind"/><br />	
</xsl:template>
 
 <!-- Policy definition-->
 <xsl:template match="policy">
    <h3>Policy</h3>
    <b>ID: </b><xsl:value-of select="@id"/><br />
    <xsl:apply-templates select="adminProperties"/>
    <b>Sequence: </b><xsl:value-of select="sequence"/><br />
    <b>Major Sequence: </b><xsl:value-of select="majorsequence"/><br />
    <b>Delimiter: </b><xsl:value-of select="delimiter"/><br />
    <b>Store: </b><xsl:apply-templates select="storeRef"/><br />
    <xsl:apply-templates select="typeRefList"/>
    <xsl:apply-templates select="formatRefList"/>
    <xsl:apply-templates select="defaultFormat"/>
    <xsl:if test='enforceLocking'>
        enforceLocking<br />
    </xsl:if>
    <xsl:if test='allowAllTypes'>
        allowAllTypes<br />
    </xsl:if>
    <xsl:if test='allowAllFormats'>
        allowAllFormats<br />
    </xsl:if>
    <xsl:apply-templates select="allstateDef"/>
    <xsl:apply-templates select="stateDefList"/>
</xsl:template>

<!-- Relationship definition-->
 <xsl:template match="relationshipDef">
    <h3>Relationship</h3>
    <b>ID: </b><xsl:value-of select="@id"/><br />
    <xsl:apply-templates select="adminProperties"/>
    <xsl:if test='accessRuleRef'>
        <b>Rule: </b><xsl:value-of select="accessRuleRef"/>
    </xsl:if>
    <xsl:if test='derivedFromRelationship'>
        <xsl:apply-templates select="derivedFromRelationship"/><br />
    </xsl:if>
    <xsl:apply-templates select="fromSide"/>
    <xsl:apply-templates select="toSide"/> 
    <xsl:apply-templates select="localAttributes"/><br />
    <xsl:apply-templates select="attributeDefRefList"/>
    <xsl:apply-templates select="triggerList"/>
    <xsl:if test='abstract'>
        <br />abstract
    </xsl:if>
    <xsl:if test='preventDuplicates'>
        <br />preventDuplicates
    </xsl:if>	
    <br /><b>Relationship Kind: </b><xsl:value-of select="relationshipKind"/><br />	
</xsl:template>

<!-- Rule definition-->
 <xsl:template match="accessRule">
    <h3>Rule</h3>
    <b>ID: </b><xsl:value-of select="@id"/><br />
    <xsl:apply-templates select="adminProperties"/><br />
    <xsl:apply-templates select="userAccessList"/><br />
</xsl:template>
 
 <!-- Interface definition-->
 <xsl:template match="interfaceType">
    <h3>Interface</h3>
    <b>ID: </b><xsl:value-of select="@id"/><br />
    <xsl:apply-templates select="adminProperties"/>
    <xsl:if test='abstract'>
        abstract<br />
    </xsl:if>
    <xsl:apply-templates select="derivedFromInterface"/><br />
    <xsl:apply-templates select="localAttributes"/><br />
    <xsl:apply-templates select="attributeDefRefList"/> <br /> 
    <xsl:apply-templates select="typeRefList"/><br /> 
    <xsl:if test='allowAllTypes'>
        allowAllTypes<br />
    </xsl:if>
    <xsl:if test='allowAllRelationships'>
        allowAllRelationships<br />
    </xsl:if>
    <xsl:apply-templates select="relationshipDefRefList"/>
</xsl:template>

<!-- Expression definition-->
 <xsl:template match="expressionObject">
    <h3>Expression</h3>
    <b>ID: </b><xsl:value-of select="@id"/><br />
    <xsl:apply-templates select="adminProperties"/><br />
    <b>Value: </b><xsl:value-of select="expression"/>
</xsl:template>

<!-- Role definition-->
 <xsl:template match="role">
    <h3>Role</h3>
    <b>ID: </b><xsl:value-of select="@id"/><br />
    <xsl:apply-templates select="adminProperties"/><br />
    <b>Role Type: </b> <xsl:value-of select="roleType"/><br />
    <b>Maturity: </b> <xsl:value-of select="maturity"/><br />
    <b>Category: </b> <xsl:value-of select="category"/><br />
    <xsl:apply-templates select="parentRole"/><br />
    <xsl:apply-templates select="homeSite"/>
</xsl:template>

<!-- Group definition-->
 <xsl:template match="group">
    <h3>Group</h3>
    <b>ID: </b><xsl:value-of select="@id"/><br />
    <xsl:apply-templates select="adminProperties"/><br />
    <xsl:apply-templates select="parentGroup"/>
    <xsl:apply-templates select="homeSite"/><br />
</xsl:template>

<!-- Person definition-->
 <xsl:template match="person">
    <h3>Person</h3>
    <b>ID: </b><xsl:value-of select="@id"/><br />
    <xsl:apply-templates select="adminProperties"/><br />
    <b>Full Name: </b> <xsl:value-of select="fullName"/><br />
    <b>Address: </b> <xsl:value-of select="address"/><br />
    <b>Phone: </b> <xsl:value-of select="phone"/><br />
    <b>Fax : </b> <xsl:value-of select="fax"/><br />
    <b>Email : </b> <xsl:value-of select="email"/><br />
    <xsl:if test='wantsIconMail'>
        wantsIconMail<br />
    </xsl:if>
    <xsl:if test='wantsEmail'>
        wantsEmail<br />
    </xsl:if>
    <xsl:if test='fullUser'>
        fullUser<br />
    </xsl:if>
    <xsl:if test='businessAdministrator'>
        businessAdministrator<br />
    </xsl:if>
    <xsl:if test='systemAdministrator'>
        systemAdministrator<br />
    </xsl:if>
    <xsl:if test='inactive'>
        inactive<br />
    </xsl:if>
    <xsl:if test='applicationsOnly'>
        applicationsOnly<br />
    </xsl:if>
    <xsl:if test='trusted'>
        trusted<br />
    </xsl:if>
    <xsl:if test='passwordChangeRequired'>
        passwordChangeRequired<br />	
    </xsl:if>
    <xsl:if test='passwordNeverExpires'>
        passwordNeverExpires<br />	
    </xsl:if>
    <xsl:apply-templates select="homeVault"/><br />
    <xsl:apply-templates select="homeSite"/><br />
    <b>Access: </b><xsl:apply-templates select="access"/><br />
    <b>Admin Access: </b><xsl:apply-templates select="adminAccess"/><br />
    <xsl:apply-templates select="defaultApplication"/><br />
    <xsl:apply-templates select="assignmentList"/>
</xsl:template>

<!-- Association definition-->
 <xsl:template match="association">
    <h3>Association</h3>
    <b>ID: </b><xsl:value-of select="@id"/><br />
    <xsl:apply-templates select="adminProperties"/><br />
    <b>Definition: </b><xsl:value-of select="definition"/>
</xsl:template>

<!-- Program definition-->
 <xsl:template match="program">
    <h3>Program</h3>
    <b>ID: </b><xsl:value-of select="@id"/><br />
    <xsl:apply-templates select="adminProperties"/><br />
    <xsl:if test='accessRuleRef'>
        <b>Rule: </b><xsl:value-of select="accessRuleRef"/><br />
    </xsl:if>
    <xsl:if test='mqlProgram'>
        mqlProgram<br />
    </xsl:if>
    <xsl:if test='needsContext'>
        needsContext<br />
    </xsl:if>
    <xsl:if test='usesInterface'>
        usesInterface<br />
    </xsl:if>
    <xsl:if test='deferred'>
        deferred<br />
    </xsl:if>
    <xsl:if test='downloadable'>
        downloadable<br />
    </xsl:if>
    <xsl:if test='mqlPipe'>
        mqlPipe<br />
    </xsl:if>
    <xsl:if test='pooled'>
        pooled<br />	
    </xsl:if>
    <xsl:if test='javaProgram'>
        javaProgram<br />	
    </xsl:if>
    <br/><b>User: </b><xsl:value-of select="userRef"/><br />
    <b>Code: </b><br /><pre><xsl:value-of select="code"/></pre>
</xsl:template>

<!-- Format definition-->
 <xsl:template match="format">
    <h3>Format</h3>
    <b>ID: </b><xsl:value-of select="@id"/><br />
    <xsl:apply-templates select="adminProperties"/><br />
    <b>Version: </b> <xsl:value-of select="version"/><br />
    <b>File Suffix: </b> <xsl:value-of select="fileSuffix"/><br />
    <b>File Creator: </b> <xsl:apply-templates select="fileCreator"/><br />
    <b>File Type: </b> <xsl:apply-templates select="fileType"/>
</xsl:template>

<!-- Page definition-->
 <xsl:template match="page">
    <h3>Page</h3>
    <b>ID: </b><xsl:value-of select="@id"/><br />
    <xsl:apply-templates select="adminProperties"/><br />
    <b>Mime Type: </b> <xsl:value-of select="mimeType"/><br />
    <b>Page Content: </b> <xsl:value-of select="pageContent"/><br />
</xsl:template>

<!-- Dimension definition-->
 <xsl:template match="dimension">
    <h3>Dimension</h3>
    <b>ID: </b><xsl:value-of select="@id"/><br />
    <xsl:apply-templates select="adminProperties"/>
    <xsl:apply-templates select="unitList"/>
</xsl:template>

 <xsl:template match="adminProperties">
    <b>Name: </b> <xsl:value-of select="name"/><br />
    <b>Description: </b> <xsl:value-of select="description"/><br />
    <b>Creation Date: </b> <xsl:apply-templates select="creationInfo"/>
    <b>Modification Date: </b> <xsl:apply-templates select="modificationInfo"/>
    <xsl:if test='hidden'>
        hidden<br />
    </xsl:if>
    <xsl:apply-templates select="propertyList"/>
<!--	History List: <br /><xsl:apply-templates select="historyList"/><br />-->
</xsl:template>

<xsl:template match="derivedFrom">
    <b>Derived from:</b> <br />
    <xsl:for-each select="typeRefList">
        <xsl:value-of select="typeRef"/><br />
    </xsl:for-each>
</xsl:template>

<xsl:template match="attributeDefRefList">
    <b>Attributes List:</b> <br />
    <xsl:for-each select="attributeDefRef">
        <xsl:value-of select="text()" /><br />
    </xsl:for-each>
</xsl:template>

<xsl:template match="localAttributes">
    <b>LocalAttributes:</b> <br />
    <xsl:for-each select="attributeDefList">
        <xsl:apply-templates select="attributeDef"/> <br />
    </xsl:for-each>
</xsl:template>

<xsl:template match="methodList">
    <b>Method List: </b><br />
    <xsl:for-each select="*">
        <xsl:value-of select="text()" /> <br />
    </xsl:for-each>
</xsl:template>

<xsl:template match="triggerList">
    <h4>Trigger List: </h4>
    <table border="2">
        <tr bgcolor="#9acdFF">
            <th>triggerName</th>
            <th>programRef</th>
            <th>inputArguments</th>
        </tr>
        <xsl:for-each select="trigger">
            <tr>
                <td><xsl:value-of select="triggerName"/></td>
                <td><xsl:value-of select="programRef"/></td>
                <td><xsl:value-of select="inputArguments"/></td>
            </tr>
        </xsl:for-each>
    </table>
</xsl:template>

<xsl:template match="creationInfo">
    <xsl:value-of select="datetime"/><br />
</xsl:template>

<xsl:template match="modificationInfo">
    <xsl:value-of select="datetime"/><br />
</xsl:template>

<xsl:template match="propertyList">
    <b>Property List: </b> <br />
    <table border="1">
        <tr bgcolor="#9acdFF">
            <th>Name</th>
            <th>Value</th>
            <th>Admin Type</th>
            <th>Admin Name</th>
            <th>Flags</th>
        </tr>
        <xsl:for-each select="property">
            <tr>
                <td><xsl:value-of select="name"/></td>
                <td><xsl:value-of select="value"/></td>
                <xsl:choose>
                    <xsl:when test="adminRef">
                        <xsl:apply-templates select="adminRef"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <td/>
                        <td/>
                    </xsl:otherwise>
                </xsl:choose>
                <td><xsl:value-of select="flags"/></td>
            </tr>
        </xsl:for-each>
    </table>
 </xsl:template>
 
 <xsl:template match="adminRef">
    <td><xsl:value-of select="adminType"/></td>
    <td><xsl:value-of select="adminName"/></td>
 </xsl:template>
 
 <xsl:template match="historyList">
    <table border="2">
        <tr bgcolor="#9acdFF">
            <th>Date/Time</th>
            <th>Agent</th>
            <th>Event</th>
            <th>Order</th>
            <th>String</th>
        </tr>
        <xsl:for-each select="history">
            <tr>
                <td><xsl:value-of select="datetime"/></td>
                <td><xsl:value-of select="agent"/></td>
                <td><xsl:value-of select="event"/></td>
                <td><xsl:value-of select="order"/></td>
                <td><xsl:value-of select="string"/></td>
            </tr>
        </xsl:for-each>
    </table>
 </xsl:template>

 <xsl:template match="fromSide">
    <h4>From Side</h4>
    <xsl:if test='meaning'>
        <b>Meaning: </b><xsl:value-of select="meaning"/><br />
    </xsl:if>
    <b>Cardinality: </b><xsl:value-of select="cardinality"/><br />
    <b>Revision Action: </b><xsl:value-of select="revisionAction"/><br />
    <b>Clone Action: </b><xsl:value-of select="cloneAction"/><br />
    <xsl:if test='allowAllTypes'>
        allowAllTypes<br />
    </xsl:if>
    <xsl:apply-templates select="typeRefList"/>
    <xsl:if test='allowAllRelationships'>
        allowAllRelationships<br />
    </xsl:if>
    <xsl:apply-templates select="relationshipDefRefList"/>
    <xsl:if test='propagateModify'>
        propagateModify<br />
    </xsl:if>
    <xsl:if test='propagateConnection'>
        propagateConnection<br />
    </xsl:if>
</xsl:template>

 <xsl:template match="toSide">
    <h4>To Side</h4>
    <xsl:if test='meaning'>
        <b>Meaning: </b><xsl:value-of select="meaning"/><br />
    </xsl:if>
    <b>Cardinality: </b><xsl:value-of select="cardinality"/><br />
    <b>Revision Action: </b><xsl:value-of select="revisionAction"/><br />
    <b>Clone Action: </b><xsl:value-of select="cloneAction"/><br />
    <xsl:if test='allowAllTypes'>
        allowAllTypes<br />
    </xsl:if>
    <xsl:apply-templates select="typeRefList"/>
    <xsl:if test='allowAllRelationships'>
        allowAllRelationships<br />
    </xsl:if>
    <xsl:apply-templates select="relationshipDefRefList"/>
    <xsl:if test='propagateModify'>
        propagateModify<br />
    </xsl:if>
    <xsl:if test='propagateConnection'>
        propagateConnection<br />
    </xsl:if>
</xsl:template>

<xsl:template match="typeRefList">
    <b>Type List: </b><br />
    <xsl:for-each select="typeRef">
        <xsl:value-of select="text()" /> <br />
    </xsl:for-each><br />
</xsl:template>

<xsl:template match="relationshipDefRefList">
    <b>Relationship List: </b><br />
    <xsl:for-each select="relationshipDefRef">
        <xsl:value-of select="text()" /> <br />
    </xsl:for-each><br />
</xsl:template>

<xsl:template match="formatRefList">
    <b>Format List: </b><br />
    <xsl:for-each select="*">
        <xsl:value-of select="text()" /> <br />
    </xsl:for-each>
</xsl:template>

<xsl:template match="defaultFormat">
    <b>Default format: </b><xsl:value-of select="formatRef" /> <br />
</xsl:template>

<xsl:template match="derivedFromRelationship">
    <b>Derived from relationship:</b><br />
    <xsl:for-each select="//relationshipDefRef">
        <xsl:value-of select="text()" /> <br />
    </xsl:for-each>
</xsl:template>

<xsl:template match="userAccessList">
    <h4>User Access List: </h4>
    <table border="2">
        <tr bgcolor="#9acdFF">
            <th>userAccessKind</th>
            <th>userRef</th>
            <th>matchOrganization</th>
            <th>matchProject</th>
            <th>matchOwner</th>
            <th>matchReserve</th>
            <th>matchMaturity</th>
            <th>matchCategory</th>
            <th>expressionFilter</th>				
            <th>Access</th>					
        </tr>
        <xsl:for-each select="userAccess">
            <tr>
                <td><xsl:value-of select="userAccessKind"/></td>
                <td><xsl:value-of select="userRef"/></td>
                <td><xsl:value-of select="matchOrganization"/></td>
                <td><xsl:value-of select="matchProject"/></td>
                <td><xsl:value-of select="matchOwner"/></td>
                <td><xsl:value-of select="matchReserve"/></td>
                <td><xsl:value-of select="matchMaturity"/></td>
                <td><xsl:value-of select="matchCategory"/></td>
                <td><xsl:value-of select="expressionFilter"/></td>
                <td><xsl:apply-templates select="access"/></td>
            </tr>
        </xsl:for-each>
    </table>
</xsl:template>

<xsl:template match="stateDefList">
    <br /><b>States: </b><br />
    <xsl:for-each select="stateDef">
        <br /><b>Name: </b><xsl:value-of select="name"/><br />
        <xsl:if test='revisionable'>
            revisionable<br />
        </xsl:if>
        <xsl:if test='majorrevisionable'>
            majorrevisionable<br />
        </xsl:if>
        <xsl:if test='versionable'>
            versionable<br />
        </xsl:if>
        <xsl:if test='autoPromotion'>
            autoPromotion<br />
        </xsl:if>
        <xsl:if test='checkoutHistory'>
            checkoutHistory<br />
        </xsl:if>
        <xsl:if test='routeMessage'>
            <b>Route Message: </b>
            <xsl:value-of select="routeMessage"/><br />
        </xsl:if>
        <xsl:if test='routeUser'>
            <b>Route User: </b>
            <xsl:value-of select="routeUser/userRef"/><br />
        </xsl:if>
        <xsl:if test='notifyMessage'>
            <b>Notify Message: </b>
            <xsl:value-of select="notifyMessage"/><br />
        </xsl:if>
        <xsl:apply-templates select="notifyUserList"/>
        <xsl:apply-templates select="actionProgram"/>
        <xsl:apply-templates select="checkProgram"/>
        <xsl:apply-templates select="userAccessList"/>
        <xsl:apply-templates select="triggerList"/>
    </xsl:for-each>
</xsl:template>

 <xsl:template match="access">
    <xsl:for-each select="*">
        <xsl:value-of select="name()"/><br />
    </xsl:for-each>
</xsl:template>

<xsl:template match="allstateDef">
    <h4>All State Access: </h4>
    <b>Name: </b><xsl:value-of select="name"/><br />
    <xsl:apply-templates select="userAccessList"/><br />
</xsl:template>

<xsl:template match="notifyUserList">
    <b>Notify User List:</b> <br />
    <xsl:for-each select="userRef">
        <xsl:value-of select="text()" /><br />
    </xsl:for-each>
</xsl:template>

 <xsl:template match="actionProgram">
    <br /><b>Action Program:</b> <br />
    <b>programRef: </b><xsl:value-of select="programRef"/><br />
    <b>inputArguments: </b><xsl:value-of  select="inputArguments"/><br />
</xsl:template>

 <xsl:template match="checkProgram">
    <br /><b>Check Program:</b> <br />
    <b>programRef: </b><xsl:value-of select="programRef"/><br />
    <b>inputArguments: </b><xsl:value-of  select="inputArguments"/><br />
</xsl:template>

<xsl:template match="rangeList">
    <h4>Range List: </h4>
    <table border="2">
        <tr bgcolor="#9acdFF">
            <th>rangeType</th>
            <th>rangeValue</th>
            <th>includingValue</th>
            <th>rangeSecondValue</th>
            <th>includingSecondValue</th>
        </tr>
    <xsl:for-each select="range">
        <tr>
            <td><xsl:value-of select="rangeType"/></td>
            <td><xsl:value-of select="rangeValue"/></td>
            <xsl:choose>
                <xsl:when test="includingValue">
                    <td>+</td>
                </xsl:when>
                <xsl:otherwise>
                    <td/>
                </xsl:otherwise>
            </xsl:choose>
            <td><xsl:value-of select="rangeSecondValue"/></td>
            <xsl:choose>
                <xsl:when test="includingSecondValue">
                    <td>+</td>
                </xsl:when>
                <xsl:otherwise>
                    <td/>
                </xsl:otherwise>
            </xsl:choose>
        </tr>
    </xsl:for-each>
    </table>
</xsl:template>

<xsl:template match="rangeProgram">
    <b>Range Program:</b> <br />
    <b>programRef: </b><xsl:value-of select="programRef"/><br />
    <b>inputArguments: </b><xsl:value-of select="inputArguments"/><br />
</xsl:template>

<xsl:template match="derivedFromInterface">
    <b>Derived from:</b> <br />
    <xsl:for-each select="interfaceTypeRefList">
        <xsl:value-of select="interfaceTypeRef"/><br />
    </xsl:for-each>
</xsl:template>

<xsl:template match="parentGroup">
    <b>Parent Group:</b> <br />
    <xsl:for-each select="groupRef">
        <xsl:value-of select="text()" /><br />
    </xsl:for-each>
</xsl:template>

<xsl:template match="parentRole">
    <b>Parent Role:</b> <br />
    <xsl:for-each select="roleRef">
        <xsl:value-of select="text()" /><br />
    </xsl:for-each>
</xsl:template>

<xsl:template match="homeVault">
    <b>Vault:</b> <br />
    <xsl:for-each select="vaultRef">
        <xsl:value-of select="text()" /><br />
    </xsl:for-each>
</xsl:template>

<xsl:template match="homeSite">
    <b>Site: </b> <br />
    <xsl:for-each select="siteRef">
        <xsl:value-of select="text()" /><br />
    </xsl:for-each>
</xsl:template>

<xsl:template match="defaultApplication">
    <b>Application: </b> <br />
    <xsl:for-each select="applicationRef">
        <xsl:value-of select="text()" /><br />
    </xsl:for-each>
</xsl:template>

 <xsl:template match="adminAccess">
    <xsl:for-each select="*">
        <xsl:value-of select="name()"/><br />
    </xsl:for-each>
</xsl:template>

<xsl:template match="assignmentList">
    <b>Assignment List:</b> <br />
    <xsl:for-each select="assignment">
        <xsl:if test='groupRef'>
            Group 
        </xsl:if>
        <xsl:if test='roleRef'>
            Role  
        </xsl:if>
        <xsl:value-of select="roleRef"/><br />
    </xsl:for-each>
</xsl:template>

<xsl:template match="unitList">
    <h4>Unit List:</h4>
    <xsl:for-each select="unit">
        <b>ID: </b><xsl:value-of select="@id"/><br />
        <xsl:apply-templates select="adminProperties"/>
        <b>Unit Label: </b><xsl:value-of select="unitLabel"/><br />
        <b>Unit Multiplier: </b><xsl:value-of select="unitMultiplier"/><br />
        <b>Unit Offset </b><xsl:value-of select="unitOffset"/><br />
        <xsl:if test='unitDefault'>
            unitDefault<br />  
        </xsl:if><br />
    </xsl:for-each>
</xsl:template>



</xsl:stylesheet>