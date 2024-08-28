// matches all children, grandchildren, etc...
/ matches only one level down. 



ClassName starting with: 
//ClassDeclaration[starts-with(@SimpleName, 'App')]

Imported packages contains:
//ImportDeclaration[contains(@ImportedName, "lombok")]

Imported package contains or ends-with: 
//ImportDeclaration[
    contains(@ImportedName, 'j')
    or ends-with(@ImportedName, 'on')
]

The number of methods which names contain "main" > 1
.[count(//MethodDeclaration[contains(@Name, 'main')]) > 1]

.[count(//LocalVariableDeclaration) > 2]




# The test case method too long:
//MethodDeclaration[
ModifierList/Annotation[@SimpleName = 'Test']
and
Block[@Size > 1]
]



# User regex:
<![CDATA[
//ClassDeclaration[matches(@SimpleName, '.*') or pmd-java:typeIs('junit.framework.TestCase')]
    (: a junit 3 method :)
    /ClassBody/MethodDeclaration[
        @Visibility="public"
        and starts-with(@Name, 'test')
        and not(ModifierList/Annotation[
          pmd-java:typeIs('org.junit.Test')
          or pmd-java:typeIs('org.junit.jupiter.api.Test')
          or pmd-java:typeIs('org.junit.jupiter.api.RepeatedTest')
          or pmd-java:typeIs('org.junit.jupiter.api.TestFactory')
          or pmd-java:typeIs('org.junit.jupiter.api.TestTemplate')
          or pmd-java:typeIs('org.junit.jupiter.params.ParameterizedTest')
          or pmd-java:typeIs('org.testng.annotations.Test')
          ]
        )
    ]
]]>