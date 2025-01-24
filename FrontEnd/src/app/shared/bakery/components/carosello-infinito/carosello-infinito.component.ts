import {ChangeDetectorRef, Component, Inject, Input, OnInit, PLATFORM_ID} from '@angular/core';
import {AdminControllerService} from "../../../../services/services/admin-controller.service";
import {isPlatformBrowser} from "@angular/common";

@Component({
  selector: 'app-carosello-infinito',
  imports: [],
  templateUrl: './carosello-infinito.component.html',
  standalone: true,
  styleUrl: './carosello-infinito.component.scss'
})
export class CaroselloInfinitoComponent implements OnInit{


  constructor(
    private adminService: AdminControllerService,
    private cdr: ChangeDetectorRef,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}



  // TODO DA FINIRE implementazione get dati random photo


  dati:any = []
  @Input() id!: number | undefined;




  ngOnInit() {
    //this.getData()
    this.cdr.detectChanges()
  }




  getData(){

    // check id

    //getData from back-end


  }





}
