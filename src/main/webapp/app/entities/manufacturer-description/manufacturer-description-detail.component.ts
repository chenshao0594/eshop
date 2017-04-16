import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { ManufacturerDescription } from './manufacturer-description.model';
import { ManufacturerDescriptionService } from './manufacturer-description.service';

@Component({
    selector: 'jhi-manufacturer-description-detail',
    templateUrl: './manufacturer-description-detail.component.html'
})
export class ManufacturerDescriptionDetailComponent implements OnInit, OnDestroy {

    manufacturerDescription: ManufacturerDescription;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private manufacturerDescriptionService: ManufacturerDescriptionService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['manufacturerDescription']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInManufacturerDescriptions();
    }

    load(id) {
        this.manufacturerDescriptionService.find(id).subscribe((manufacturerDescription) => {
            this.manufacturerDescription = manufacturerDescription;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.manufacturerDescription.id !== undefined) {
            this.manufacturerDescriptionService.update(this.manufacturerDescription)
                .subscribe((res: ManufacturerDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.manufacturerDescriptionService.create(this.manufacturerDescription)
                .subscribe((res: ManufacturerDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ManufacturerDescription) {
        this.eventManager.broadcast({ name: 'manufacturerDescriptionModification', content: 'OK'});
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

    registerChangeInManufacturerDescriptions() {
        this.eventSubscriber = this.eventManager.subscribe('manufacturerDescriptionListModification', (response) => this.load(this.manufacturerDescription.id));
    }
}
