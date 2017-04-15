import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { ZoneDescription } from './zone-description.model';
import { ZoneDescriptionService } from './zone-description.service';

@Component({
    selector: 'jhi-zone-description-detail',
    templateUrl: './zone-description-detail.component.html'
})
export class ZoneDescriptionDetailComponent implements OnInit, OnDestroy {

    zoneDescription: ZoneDescription;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private zoneDescriptionService: ZoneDescriptionService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['zoneDescription']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInZoneDescriptions();
    }

    load(id) {
        this.zoneDescriptionService.find(id).subscribe((zoneDescription) => {
            this.zoneDescription = zoneDescription;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.zoneDescription.id !== undefined) {
            this.zoneDescriptionService.update(this.zoneDescription)
                .subscribe((res: ZoneDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.zoneDescriptionService.create(this.zoneDescription)
                .subscribe((res: ZoneDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ZoneDescription) {
        this.eventManager.broadcast({ name: 'zoneDescriptionModification', content: 'OK'});
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

    registerChangeInZoneDescriptions() {
        this.eventSubscriber = this.eventManager.subscribe('zoneDescriptionListModification', (response) => this.load(this.zoneDescription.id));
    }
}
