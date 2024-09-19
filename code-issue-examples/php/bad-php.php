<?php

// The drupal_set_message() function is being deprecated!
 // @see https://api.drupal.org/api/drupal/core%21includes%21bootstrap.inc/function/drupal_set_message/8.5.x
 // > Deprecated in Drupal 8.5.0 and will be removed before Drupal 9.0.0.
 // > Use \Drupal\Core\Messenger\MessengerInterface::addMessage() instead.

 // In some custom code.
 \Drupal::messenger()->addMessage('Say something else');

 // When trying to print out a simple var.
 \Drupal::messenger()->addMessage(print_r($stuff, TRUE));

 // In a Drupal 8 Form's submitForm() handler:
 $this->messenger()->addMessage($this->t('Hello world.'));

 // Add specific type of message.
 $this->messenger()->addMessage('Hello world', 'custom');

 // Add error message.
 $this->messenger()->addError('Hello world');

 // Add status message.
 $this->messenger()->addStatus('Hello world');

 // Add warning message.
 $this->messenger()->addWarning('Hello world');