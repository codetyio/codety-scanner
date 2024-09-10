public function getName(): string
{
    return $this->name;
}

if ($this->getName() === 'Foo') {
    echo $this->getName(); // still 'Foo'
}

public function getRandomNumber(): int
{
    return rand();
}

if ($this->getRandomNumber() === 4) {
    echo $this->getRandomNumber(); // it's not going to be 4 but PHPStan will think it is
}

// "Class has an uninitialized property $foo. Give it default value or assign it in the constructor."
private int $foo;

public function setFoo(int $foo): void
{
	$this->foo = $foo;
}