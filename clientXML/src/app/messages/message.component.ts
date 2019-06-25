
import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MessageService} from '../service/message.service';

@Component({
  templateUrl: './message.component.html'
})

export class MessageComponent implements OnInit {

  empty = false;

  responses = [];
  messages = [];

  public form: FormGroup;
  public text: AbstractControl;

  constructor(protected  router: Router,
              protected route: ActivatedRoute,
              private fb: FormBuilder,
              private messageService: MessageService){
    this.form = this.fb.group({
      'text': ['', Validators.compose([Validators.required])],

    })
    this.text = this.form.controls['text'];

  }
  ngOnInit() {
    this.empty = false;

    this.messageService.getAllResponses().subscribe(data => {
      this.responses = data;
      if (this.responses.length === 0) {
        this.empty = true;
      }
    })

    /*this.messageService.getAllMessages().subscribe(data => {
      this.messages = data;
      if (this.messages.length === 0) {
        this.empty = true;
      }
    })*/
  }

}
