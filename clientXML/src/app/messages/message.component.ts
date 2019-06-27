
import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MessageService} from '../service/message.service';
import {MessageModel} from 'app/model/message.model';

@Component({
  templateUrl: './message.component.html'
})

export class MessageComponent implements OnInit {

  empty = false;
  write = false;

  messages = [];
  responses = [];
  agents = []

  public form: FormGroup;
  public text: AbstractControl;
  public agent: AbstractControl;

  constructor(protected  router: Router,
              protected route: ActivatedRoute,
              private fb: FormBuilder,
              private messageService: MessageService) {
    this.form = this.fb.group({
      'text': ['', Validators.compose([Validators.required])],
      'agent': [''],

    })
    this.text = this.form.controls['text'];
    this.agent = this.form.controls['agent'];
  }

  ngOnInit() {
    this.empty = false;
    this.write = false;

    this.messageService.getAllMessages().subscribe(data => {
      this.messages = data;

      this.messageService.getAllResponses().subscribe(data => {
        this.responses = data;
      })
    })

    this.messageService.getAppropriateAgents().subscribe(data =>{
      this.agents = data;
    })

  }

  createMessage() {
    this.messageService.checkIfHasReservation().subscribe(data => {
      if(data == true){
        this.write = true;
      } else{
        alert('Slanje poruke je moguće samo ako ste prethodno rezervisali smeštaj!');
      }
    })
  }

  sendMessage() {
    const message = new MessageModel(
      this.agent.value,
      this.text.value
    )

    this.messageService.sendToAgent(message).subscribe(data => {
     // this.router.navigateByUrl('/welcomepage');
      location.reload();
    })

  }
}
