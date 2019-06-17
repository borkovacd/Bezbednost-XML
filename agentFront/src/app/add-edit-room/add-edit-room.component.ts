
import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {RoomModel} from "../model/room.model";
import {RoomService} from "../service/room.service";

@Component({
  templateUrl: './add-edit-room.component.html'
})

export class AddEditRoomComponent implements  OnInit{
  public form: FormGroup;
  public capacity: AbstractControl;
  public floor: AbstractControl;
  public hasBalcony: AbstractControl;
  public day: AbstractControl;


  public method_name = 'DODAJ';
  constructor(protected  router: Router,
              public fb: FormBuilder,
              private route: ActivatedRoute,
              private roomService: RoomService,){
    this.form = this.fb.group({
      'capacity': ['', Validators.compose([Validators.required])],
      'floor': ['', Validators.compose([Validators.required])],
      'hasBalcony': [''],
      'day': ['', Validators.compose([Validators.required])],


    })
    this.capacity = this.form.controls['capacity'];
    this.floor = this.form.controls['floor'];
    this.hasBalcony = this.form.controls['hasBalcony'];
    this.day = this.form.controls['day'];

  }
  ngOnInit(){
    const mode = this.route.snapshot.params.mode;
    const idR = this.route.snapshot.params.idR;
    const idAccommodation = this.route.snapshot.params.idA;


    if (mode == 'edit') {
      this.method_name = 'IZMENI';
      this.roomService.getOneRoom(idAccommodation, idR).subscribe(data => {
        this.form.controls['capacity'].setValue(data.capacity);
        this.form.controls['floor'].setValue(data.floor);
        this.form.controls['hasBalcony'].setValue(data.hasBalcony);
        this.form.controls['day'].setValue(data.day);
      })
    } else if (mode == 'add') {
      this.method_name = 'DODAJ';
    }
  }

  confirmClick(){
    if (this.method_name === 'DODAJ') {
      this.createRoom();
    } else {
      this.editRoom();
    }
  }
  createRoom(){
    const idAccommodation = this.route.snapshot.params.idA;

    const room = new RoomModel(
      this.capacity.value,
      this.floor.value,
      this.hasBalcony.value,
      this.day.value
    );
    this.roomService.createRoom(room,idAccommodation).subscribe(data =>{
      this.router.navigateByUrl('welcomepage/room/' +  idAccommodation   );
    })
  }
  editRoom(){
    const idAccommodation = this.route.snapshot.params.idA;
    const idR = this.route.snapshot.params.idR;

    const room = new RoomModel(
      this.capacity.value,
      this.floor.value,
      this.hasBalcony.value,
      this.day.value
    );
    this.roomService.editRoom(room, idAccommodation, idR).subscribe(data => {
      this.router.navigateByUrl('welcomepage/room/' + idAccommodation );

    })

  }
  exit(){
    const idA = this.route.snapshot.params.idA;

    this.router.navigateByUrl('welcomepage/room/' +  idA   );
  }
}
