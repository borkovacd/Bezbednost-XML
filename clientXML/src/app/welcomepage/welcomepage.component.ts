import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ReservationService} from '../service/reservation.service';
import {v} from '@angular/core/src/render3';
import {AdvancedSearchModel} from '../model/advancedSearch.model';
import {SearchModel} from '../model/search.model';
import {Room} from '../model/room.model';
import {ReservationModel} from '../model/reservation.model';


@Component ({
  templateUrl: './welcomepage.component.html',
  styleUrls: ['./welcomepage.component.scss']
})

export  class WelcomepageComponent {

  advancedSearch = false;


  public fromDate: any;
  public toDate: any;
  public items: Room[];
  public form: FormGroup;
  public advancedSearchform: FormGroup;
  public tipHotel: AbstractControl;
  public tipBadAndBreakfast: AbstractControl;
  public tipApartman: AbstractControl;
  public kategorija1: AbstractControl;
  public kategorija2: AbstractControl;
  public kategorija3: AbstractControl;
  public kategorija4: AbstractControl;
  public kategorija5: AbstractControl;
  public nekategorisan: AbstractControl;
  public parking: AbstractControl;
  public wifi: AbstractControl;
  public dorucak: AbstractControl;
  public poluPansion: AbstractControl;
  public pansion: AbstractControl;
  public allInclusive: AbstractControl;
  public petFriendly: AbstractControl;
  public tv: AbstractControl;
  public miniKuhinja: AbstractControl;
  public kupatilo: AbstractControl;
  public besplatnoOtkazivanje: AbstractControl;
  public city: AbstractControl;
  public checkInDate: AbstractControl;
  public checkOutDate: AbstractControl;
  public numberOfPersons: AbstractControl;

  constructor(protected router: Router,
              public fb: FormBuilder,
              private route: ActivatedRoute,
              private reservationService: ReservationService,) {
    this.form = this.fb.group({
      'city': ['', Validators.compose([Validators.required])],
      'checkInDate': ['', Validators.compose([Validators.required])],
      'checkOutDate': ['', Validators.compose([Validators.required])],
      'numberOfPersons': ['', Validators.compose([Validators.required])],
    }),
    this.advancedSearchform = this.fb.group({
        'tipHotel': [''],
        'tipApartman': [''],
        'tipBadAndBreakfast': [''],
        'kategorija1': [''],
        'kategorija2': [''],
        'kategorija3': [''],
        'kategorija4': [''],
        'kategorija5': [''],
        'nekategorisan': [''],
        'parking': [''],
        'wifi': [''],
        'dorucak': [''],
        'poluPansion': [''],
        'pansion': [''],
        'kupatilo': [''],
        'miniKuhinja': [''],
        'allInclusive': [''],
        'tv': [''],
        'petFriendly': [''],
        'besplatnoOtkazivanje': [''],
    })
    this.city = this.form.controls['city'];
    this.checkInDate = this.form.controls['checkInDate'];
    this.checkOutDate = this.form.controls['checkOutDate'];
    this.numberOfPersons = this.form.controls['numberOfPersons'];
    this.tipHotel = this.advancedSearchform.controls['tipHotel'];
    this.tipApartman = this.advancedSearchform.controls['tipApartman'];
    this.tipBadAndBreakfast = this.advancedSearchform.controls['tipBadAndBreakfast'];
    this.kategorija1 = this.advancedSearchform.controls['kategorija1'];
    this.kategorija2 = this.advancedSearchform.controls['kategorija2'];
    this.kategorija3 = this.advancedSearchform.controls['kategorija3'];
    this.kategorija4 = this.advancedSearchform.controls['kategorija4'];
    this.kategorija5 = this.advancedSearchform.controls['kategorija5'];
    this.nekategorisan = this.advancedSearchform.controls['nekategorisan'];
    this.parking = this.advancedSearchform.controls['parking'];
    this.wifi = this.advancedSearchform.controls['wifi'];
    this.dorucak = this.advancedSearchform.controls['dorucak'];
    this.poluPansion = this.advancedSearchform.controls['poluPansion'];
    this.pansion = this.advancedSearchform.controls['pansion'];
    this.allInclusive = this.advancedSearchform.controls['allInclusive'];
    this.petFriendly = this.advancedSearchform.controls['petFriendly'];
    this.tv = this.advancedSearchform.controls['tv'];
    this.miniKuhinja = this.advancedSearchform.controls['miniKuhinja'];
    this.kupatilo = this.advancedSearchform.controls['kupatilo'];
    this.besplatnoOtkazivanje = this.advancedSearchform.controls['besplatnoOtkazivanje'];
  }



  ngOnInit() {

    this.advancedSearch = false;
  }

  login(){
    this.router.navigateByUrl('login');
  }
  registration(){
    this.router.navigateByUrl('registration');
  }
  logOut(){
    this.router.navigateByUrl('');
  }
  messages(){
    this.router.navigateByUrl('messages');
  }
  serachRooms(){

    if ( this.checkInDate.value > this.checkOutDate.value ) {
      alert('Molimo ponovo unesite datum dolaska i odlaska.');
      this.checkInDate.reset();
      this.checkOutDate.reset();
      return;
    }

    this.fromDate = this.checkInDate.value;
    this.toDate = this.checkOutDate.value;

    const object = new SearchModel(
      this.city.value,
      this.checkInDate.value,
      this.checkOutDate.value,
      this.numberOfPersons.value
    );

    this.reservationService.searchFreeRooms(object).subscribe(data => {
        this.items = data;
    });

    this.advancedSearch = true;

  }
  reservationRoom(item: number) {

    const resDto = new ReservationModel();

    resDto.idRoom = item;
    resDto.fromDate = this.fromDate;
    resDto.toDate = this.toDate;
    resDto.confirmed = false;

    this.reservationService.makeRes(resDto).subscribe( data => {
      this.router.navigateByUrl('/home');
    });
  }


  advancedSearchRooms() {

    const object = new AdvancedSearchModel(
      this.tipHotel.value,
      this.tipApartman.value,
      this.tipBadAndBreakfast.value,
      this.kategorija1.value,
      this.kategorija2.value,
      this.kategorija3.value,
      this.kategorija4.value,
      this.kategorija5.value,
      this.nekategorisan.value,
      this.parking.value,
      this.wifi.value,
      this.dorucak.value,
      this.poluPansion.value,
      this.pansion.value,
      this.allInclusive.value,
      this.petFriendly.value,
      this.tv.value,
      this.miniKuhinja.value,
      this.kupatilo.value,
      this.besplatnoOtkazivanje.value,
      this.items
    );

    this.reservationService.advancedSearchFreeRooms(object).subscribe(data => {
      this.items = data;
    });

  }
}
