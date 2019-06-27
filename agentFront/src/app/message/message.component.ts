
import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MessageService} from '../service/message.service';
import {AnswerModel} from '../model/answer.model';
import {ResponseService} from '../service/response.service';

@Component({
  selector: 'messagess',
  templateUrl: './message.component.html'
})

export class MessageComponent implements OnInit{
  answer = false;
  empty = false;

  messages = [];
  responses = [];
  username = '';
  idSender;
  idMessage;
  idTo;

  public form: FormGroup;
  public text: AbstractControl;

  constructor(protected  router: Router,
              protected route: ActivatedRoute,
              private fb: FormBuilder,
              private messageService: MessageService,
              private responseService: ResponseService){
    this.form = this.fb.group({
      'text': ['', Validators.compose([Validators.required])],

    })
    this.text = this.form.controls['text'];

  }
  ngOnInit(){
    this.empty = false;

    this.messageService.getAllMessages().subscribe(data => {
      this.messages = data;

      this.responseService.getAllResponses().subscribe(data => {
        this.responses = data;
      })
    })

  }

  escapeCharacters(value: string): string{
    return value
      .replace(/&/g, '&amp;')
      .replace(/</g, '&lt;')
      .replace(/>/g, '&gt;')
      .replace(/\"/g, '&quot;')
      /*.replace(/\'/g, '&#39;')*/
      .replace(/\//g, '&#x2F;')
      .replace('src', 'drc')
      .replace(/\'/g, '&apos')

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
    const username = this.route.snapshot.params.username;
    console.log(
      this.text.value

    )
    const answer = new AnswerModel(
      username,
      this.idTo,
      this.escapeCharacters(this.text.value),
      this.idMessage,
    );

    this.messageService.answerToClient(answer).subscribe(data => {
      this.router.navigateByUrl( '/welcomepage');
      location.reload();
    })

  }



}
