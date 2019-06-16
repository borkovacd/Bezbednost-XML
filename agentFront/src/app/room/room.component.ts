
import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

@Component ({
  templateUrl: './room.component.html',

})

export  class RoomComponent implements OnInit{


  constructor(protected  router: Router,
              private route: ActivatedRoute,){}
  ngOnInit(){

  }

  addRoom(){
    const idA = this.route.snapshot.params.idA;

    this.router.navigateByUrl('welcomepage/room/' +  idA  + '/add' );
  }
}
