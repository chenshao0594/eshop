import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { Country } from './country.model';
import { CountryService } from './country.service';

@Component({
    selector: 'jhi-country-detail',
    templateUrl: './country-detail.component.html'
})
export class CountryDetailComponent implements OnInit, OnDestroy {

    country: Country;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private countryService: CountryService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['country']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCountries();
    }

    load(id) {
        this.countryService.find(id).subscribe((country) => {
            this.country = country;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.country.id !== undefined) {
            this.countryService.update(this.country)
                .subscribe((res: Country) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.countryService.create(this.country)
                .subscribe((res: Country) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: Country) {
        this.eventManager.broadcast({ name: 'countryModification', content: 'OK'});
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

    registerChangeInCountries() {
        this.eventSubscriber = this.eventManager.subscribe('countryListModification', (response) => this.load(this.country.id));
    }
}
