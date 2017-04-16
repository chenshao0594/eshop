import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

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
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private manufacturerService: ManufacturerService,
        private route: ActivatedRoute,
        private alertService: AlertService
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
    save() {
        this.isSaving = true;
        if (this.manufacturer.id !== undefined) {
            this.manufacturerService.update(this.manufacturer)
                .subscribe((res: Manufacturer) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.manufacturerService.create(this.manufacturer)
                .subscribe((res: Manufacturer) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: Manufacturer) {
        this.eventManager.broadcast({ name: 'manufacturerModification', content: 'OK'});
        this.isSaving = false;
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInManufacturers() {
        this.eventSubscriber = this.eventManager.subscribe('manufacturerListModification', (response) => this.load(this.manufacturer.id));
    }
}
