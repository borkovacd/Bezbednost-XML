
import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {ReservationService} from '../service/reservation.service';
import {ReservationAgentService} from '../service/reservationAgent.service';

@Component({
  selector: 'reservations',
  templateUrl: './reservation.component.html',
})

export class ReservationComponent implements  OnInit {
  reservations = [];
  items= [];

  vis: boolean = false;

  constructor(protected router: Router,
              public reservationService: ReservationService,
              public reservationAgentService: ReservationAgentService) {
  }

  ngOnInit() {

    const idAgent = localStorage.getItem('agentId');

    if(idAgent != null) {
      this.vis = false;
      this.reservationAgentService.getAllReservationAgent(idAgent).subscribe(data => {
        this.items = data;
      })
      this.reservationService.getAllReservation(idAgent).subscribe(data => {
        this.reservations = data;
      });

    } else {
      this.vis = true;
    }
  }
  confirmeReservation(id: any) {
  this.reservationService.confirmeReservation(id).subscribe(data => {

  });
    location.reload();
  }

  addReservations(){
    this.router.navigateByUrl('welcomepage/reservation/add');

  }
}
