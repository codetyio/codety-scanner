<?php
$someData = \MyNamespace\MyORM\MyRepository::findAllBySomething(SOMETHING);

foreach ($someData as $myEntity) {
	$myEntity->doTheThing();
}