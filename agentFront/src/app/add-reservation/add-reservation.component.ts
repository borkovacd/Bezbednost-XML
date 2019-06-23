import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {RoomService} from '../service/room.service';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ReservationAgentService} from '../service/reservationAgent.service';
import {SearchRoomModel} from '../model/searchRoom.model';
import {ReservationModel} from '../model/reservation.model';

@Component({
  templateUrl: './add-reservation.component.html'
})

export class AddReservationComponent implements OnInit{
  public form: FormGroup
  public checkInDate: AbstractControl;
  public checkOutDate: AbstractControl;

  items = [];
  dateFrom: any;
  dateTo: any;

  constructor(protected router: Router,
              private roomService: RoomService,
              public fb: FormBuilder,
              private route: ActivatedRoute,
              private reservationAgentService: ReservationAgentService){
    this.form = this.fb.group({
      'checkInDate': ['', Validators.compose([Validators.required])],
      'checkOutDate': ['', Validators.compose([Validators.required])],



    })
    this.checkInDate = this.form.controls['checkInDate'];
    this.checkOutDate = this.form.controls['checkOutDate'];
  }
  ngOnInit(){
    const idAgent = localStorage.getItem('agentId');
  }
  search(){
    const object = new SearchRoomModel(
      this.checkInDate.value,
      this.checkOutDate.value
    );
    this.reservationAgentService.searchRoom(object).subscribe(data => {
      this.dateFrom = this.checkInDate.value;
      this.dateTo = this.checkOutDate.value;
      this.items = data;
    })

  }
  reservationRoom(idRoom: any){
    const object = new ReservationModel(
      this.dateFrom,
      this.dateTo
    );
    this.reservationAgentService.createReservationAgent(object, idRoom).subscribe(data => {
      this.router.navigateByUrl('/welcomepage' );

    })

  }

}
