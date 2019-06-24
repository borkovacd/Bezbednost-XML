
import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MessageService} from '../service/message.service';
import {AnswerModel} from '../model/answer.model';

@Component({
  selector: 'messagess',
  templateUrl: './message.component.html'
})

export class MessageComponent implements OnInit{
  answer = false;
  empty = false;

  messages = [];
  username = '';
  idSender;
  idMessage;
  idTo;

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
  ngOnInit(){
    this.empty = false;
    this.messageService.getAllMessages().subscribe(data => {
      this.messages = data;
      if (this.messages.length === 0) {
        this.empty = true;
      }
    })
  }
  answerMessage(idSender: any, username: any, idMessage: any) {
    this.idSender = '';
    this.idTo = '';
    this.idMessage = '';
    this.answer = false;
    this.idTo = idSender;
    this.idMessage = idMessage;
    this.idSender += username;
    console.log(this.idSender)
    this.answer = true;
  }

  sendAnswer() {
    const token = localStorage.getItem('agentId');

    const object = new AnswerModel(
      token,
      this.idTo,
      this.text.value,
      this.idMessage,
    );

    this.messageService.answerToClient(object).subscribe(data => {
      this.router.navigateByUrl('/welcomepage' );

    })
  }
}
