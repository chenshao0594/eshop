import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { CountryDescription } from './country-description.model';
import { CountryDescriptionService } from './country-description.service';

@Component({
    selector: 'jhi-country-description-detail',
    templateUrl: './country-description-detail.component.html'
})
export class CountryDescriptionDetailComponent implements OnInit, OnDestroy {

    countryDescription: CountryDescription;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private countryDescriptionService: CountryDescriptionService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['countryDescription']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCountryDescriptions();
    }

    load(id) {
        this.countryDescriptionService.find(id).subscribe((countryDescription) => {
            this.countryDescription = countryDescription;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.countryDescription.id !== undefined) {
            this.countryDescriptionService.update(this.countryDescription)
                .subscribe((res: CountryDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.countryDescriptionService.create(this.countryDescription)
                .subscribe((res: CountryDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: CountryDescription) {
        this.eventManager.broadcast({ name: 'countryDescriptionModification', content: 'OK'});
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

    registerChangeInCountryDescriptions() {
        this.eventSubscriber = this.eventManager.subscribe('countryDescriptionListModification', (response) => this.load(this.countryDescription.id));
    }
}
