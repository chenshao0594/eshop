import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { Manufacturer } from './manufacturer.model';
import { ManufacturerService } from './manufacturer.service';

@Component({
    selector: 'jhi-manufacturer-detail',
    templateUrl: './manufacturer-detail.component.html'
})
export class ManufacturerDetailComponent implements OnInit, OnDestroy {

    manufacturer: Manufacturer;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private manufacturerService: ManufacturerService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['manufacturer']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInManufacturers();
    }

    load(id) {
        this.manufacturerService.find(id).subscribe((manufacturer) => {
            this.manufacturer = manufacturer;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInManufacturers() {
        this.eventSubscriber = this.eventManager.subscribe('manufacturerListModification', (response) => this.load(this.manufacturer.id));
    }
}
