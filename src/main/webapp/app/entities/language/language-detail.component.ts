import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { Language } from './language.model';
import { LanguageService } from './language.service';

@Component({
    selector: 'jhi-language-detail',
    templateUrl: './language-detail.component.html'
})
export class LanguageDetailComponent implements OnInit, OnDestroy {

    language: Language;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private languageService: LanguageService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['language']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLanguages();
    }

    load(id) {
        this.languageService.find(id).subscribe((language) => {
            this.language = language;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.language.id !== undefined) {
            this.languageService.update(this.language)
                .subscribe((res: Language) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.languageService.create(this.language)
                .subscribe((res: Language) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: Language) {
        this.eventManager.broadcast({ name: 'languageModification', content: 'OK'});
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

    registerChangeInLanguages() {
        this.eventSubscriber = this.eventManager.subscribe('languageListModification', (response) => this.load(this.language.id));
    }
}
