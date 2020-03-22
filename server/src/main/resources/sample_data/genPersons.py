from random import randint, randrange, choice

last_names = ['Peters', 'Müller', 'Schulz', 'Schulze', 'Weber', 'Wagner', 'Richter', 'Klein', 'Bauer', 'Schröder',
              'Lange', 'Winkler', 'Winter']

male_first_names = ['Peter', 'Daniel', 'Hans', 'Franz', 'Karl', 'Tim', 'Jan', 'Jens', 'Kai']
female_fist_names = ['Jana', 'Lisa', 'Anna', 'Annika', 'Petra', 'Marie', 'Susanne', 'Daniela', 'Petra']

genders = ['männlich', 'weiblich']

email_providers = ['sender', 'postmailer', ]


def gen_date_of_birth():
    year = 2020 - randint(15, 95)
    month = randrange(12) + 1
    day = randrange(28) + 1 if month == 2 else randrange(31) + 1
    return f'{year}-{month}-{day}'





def main():
    gender = choice(genders)
    firstName = choice(male_first_names) if gender == 'männlich' else choice(female_fist_names)

    person = {
        'lastName': choice(last_names),
        'firstName': firstName,
        'gender': gender,
        'dateOfBirth': gen_date_of_birth(),
        'email': f'{firstName[0]}@{choice(email_providers)}.de',
        'phoneNumber': 'telefonNr',
        'street': 'Street',
        'houseNumber': 'HouseNr',
        'zip': '654321',
        'city': 'City',
        'insuranceCompany': 'insurance',
        'insuranceMembershipNumber': 'insuranceMembershipNr',
        'fluImmunization': 'true',
        'speedOfSymptomsOutbreak': 'slow',
        'symptoms': [
            'cold',
            'cough'
        ],
        'coronaContacts': 'false',
        'riskAreas': [
            'Southcorea',
            'China',
            'France'
        ],
        'weakenedImmuneSystem': 'false',
        'preIllnesses': [
            'cancer',
            'circulatory disorder'
        ]
    }


if __name__ == '__main__':
    main()
