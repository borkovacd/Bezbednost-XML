import {Room} from './room.model';

export class AdvancedSearchModel{
  constructor(public tipHotel: boolean,
              public tipApartman: boolean,
              public tipBadAndBreakfast: boolean,
              public kategorija1: boolean,
              public kategorija2: boolean,
              public kategorija3: boolean,
              public kategorija4: boolean,
              public kategorija5: boolean,
              public nekategorisan: boolean,
              public parking: boolean,
              public wifi: boolean,
              public dorucak: boolean,
              public poluPansion: boolean,
              public pansion: boolean,
              public allInclusive: boolean,
              public petFriendly: boolean,
              public tv: boolean,
              public miniKuhinja: boolean,
              public kupatilo: boolean,
              public bespaltnoOtkazivanje: boolean,
              public rooms : Room[]){}
}
