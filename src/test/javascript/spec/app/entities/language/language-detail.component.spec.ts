import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { LanguageDetailComponent } from '../../../../../../main/webapp/app/entities/language/language-detail.component';
import { LanguageService } from '../../../../../../main/webapp/app/entities/language/language.service';
import { Language } from '../../../../../../main/webapp/app/entities/language/language.model';

describe('Component Tests', () => {

    describe('Language Management Detail Component', () => {
        let comp: LanguageDetailComponent;
        let fixture: ComponentFixture<LanguageDetailComponent>;
        let service: LanguageService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [LanguageDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    LanguageService,
                    EventManager
                ]
            }).overrideComponent(LanguageDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LanguageDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LanguageService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Language(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.language).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
